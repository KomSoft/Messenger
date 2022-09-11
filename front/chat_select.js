var gUsers;

function mess_getUsersList() {
   var xhttp = new XMLHttpRequest();
   var request = host_users;
   var debug = '[debug] Request: ' + request;
   gUsers = new Array;
   xhttp.open('GET', request , false);
   xhttp.send();
      debug = debug + '<br>[debug] Response status: ' + xhttp.status;
      if (xhttp.status == 200) {
         debug += '<br>[debug] Response: ' + xhttp.responseText;
         var users_resp = JSON.parse(xhttp.responseText);  
         for (var i = 0; i < users_resp.length; i++) {
            gUsers.push([ users_resp[i].id, users_resp[i].name]);
         }   
         console.log(gUsers);
      }
   document.getElementById("debug_frame").innerHTML = debug;
   return gUsers;
}	

function _getUserById(id) {
   for (i = 0; i < gUsers.length; i++) {
      if (parseInt(gUsers[i][0]) == id) {
         return gUsers[i][1];
      }
   }
   return 'unknown user';
}
	
function mess_getChatListForUser(user_id) {
   var xhttp = new XMLHttpRequest();
   var request = host_chats_users + '/' + user_id + '/user';
   var debug = '<br>[debug] Request: ' + request;
   var result = new Array;
   xhttp.open('GET', request , false);
   xhttp.send();
   if (xhttp.status == 200) {
      var chats = JSON.parse(xhttp.responseText); 
      for (var i = 0; i < chats.length; i++) {
         var elem = new Array(2);
         elem[0] = chats[i].id;
         elem[1] = chats[i].name;
         result.push(elem);
      }
   }
   document.getElementById("debug_frame").innerHTML = debug;
   console.log(result);
	return result;
}

function getStatusesByMessageId(id) {
  	var xhttp = new XMLHttpRequest();
   var request = host_status + '/' + id + '/message';
   var debug = '<br>[debug] Request: ' + request;
   xhttp.open('GET', request , false);
   xhttp.send();
	if (xhttp.status == 200) {
      var statuses = JSON.parse(xhttp.responseText);
   } else {
		console.log('[getStatusesByMessageId] id:' + id + ' -> Error. Response status: ' + xhttp.status);
      statuses = null;
   }
	return statuses;
}

function getMessageInfo(id) {
	statuses = getStatusesByMessageId(id);
	var res = '';
	if (statuses != null) {
      for (i2 = 0; i2 < statuses.length; i2++) {
         res += _getUserById(statuses[i2].userId) + ' - ' + statuses[i2].status + '\n';
      }
	} else {
      res = 'No statuses for this message.';
   }
   alert('Statuses for message Id:' + id + '\n' + res);
}

function _getMessageStatusByUserId(user_id, statuses) {
	if (statuses != null) {
      for (i = 0; i < statuses.length; i++) {
			if (statuses[i].userId == user_id) {
				return statuses[i].status;
			}
      }
	}
 	return 'no status';  
}

function getStatusByMessageIdByUserId(id, user_id) {
	statuses = getStatusesByMessageId(id);
   return _getMessageStatusByUserId(user_id, statuses);
}

function editMessage(id) {
// get edit message TODO  - write sevice & request
   alert('Author can edit his message, id:' + id + '\n API not exists yet');
}

function createDivMessage(user_id, message) {
   var res =  '<tr id="messageId' + message.id + '"><td class="';
   var td1 = user_id == message.userId ? 'td_author_info' : 'td_mes_info';
   var td2 = user_id == message.userId ? 'td_author_text' : 'td_mes_text';
   res += td1 + '"><b>' + message.userName + '</b><br><br>photo</td><td class="' + td2 + '">';
   res += '<i>Posted at: ' + message.dateTime + '';
   res += ' (' + _getMessageStatusByUserId(user_id, message.statuses) + ') ';
   res += '</i>&nbsp;&nbsp;&nbsp;&nbsp;';
   res += '<button onclick="getMessageInfo(' + message.id + ' )">See statuses</button>';
   if (user_id == message.userId) {
      res += '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="editMessage(' + message.id + ' )">Edit message</button>';
   }   
   res += '<p>' + message.messageText + '</p>';
   if (message.fileId != null) {
      res += 'attached file: "' + message.fileName + '" (id:' + message.fileId + ', ';
      res += message.fileType + ')';
   }
   res += '</td></tr>';
   return res;
}

function createMessage(chat_id, user_id) {
   var file_name = document.getElementById("attached_file").files[0].name;
   var file_id = null;
// TODO - we can save message only if file_id or text != null
   if (file_name != '') {
		file_id = common_saveFile(file_name);
   }
   text = document.getElementById("message_text").value;
   if (text != '') {
      var result = messages_create(chat_id, user_id, file_id, text); 
   }
   alert('Save message result:\n' + result + '\nPlease update chat!');
   getChatMessages(chat_id);
}

function addFormCreateMessage(chat_id, user_id) {
   res = '<tr><td></td><td class="td_create"><form action="#"><fieldset class="fieldset1"><legend>Reply (create new message):</legend>';
   res += '<label class="label1" for="message_text" >Your message:</label>';
   res += '<input id="message_text" size=65 placeholder="Message text (up to 255 chars)"><br>';
   res += '<label class="label1" for="attached_file" >Attach file:</label>';
   res += '<input id="attached_file" type="file"><br>'
   res += '<button onclick="createMessage('+ chat_id + ',' + user_id + ')">';
   res += 'Save message</button>&nbsp;&nbsp;&nbsp;&nbsp;';
//   res += '<button type="reset" value="Reset">Cancel</button>'
   res += '</fieldset></form></td></tr>'
   return res;
}

function mess_getChatMessages(chat_id, user_id) {
   var debug = '[debug] userId: ' + user_id + ', chatId: ' + chat_id;
//    get chat info     
   var xhttp = new XMLHttpRequest();
	var request = host_chats + '/' + chat_id;
   debug += '<br>[debug] Request: ' + request;
   xhttp.open('GET', request , false);
   xhttp.send();
   if (xhttp.status == 200) {
      chat = JSON.parse(xhttp.responseText);
      html = '<h2 align=center style="color:blue">' + chat.name + '</h2>';
      html += '<h3 align=center>' + chat.description + ' (' + chat.chatType + ')</h3>';
      html += '<table class="table_message">';
//    get messages      
      xhttp = new XMLHttpRequest();
      request = host_messages + "/" + chat_id + "/chat/" + user_id;
      debug += '<br>[debug] Request: ' + request;
      xhttp.open('GET', request , false);
      xhttp.send();
      if (xhttp.status == 200) {
         messages = JSON.parse(xhttp.responseText);
         for (k = 0; k < messages.length; k++) {
            html += createDivMessage(user_id, messages[k]);
         }
         html += addFormCreateMessage(chat_id, user_id);
         html += '</table>';
         if (messages.length == 0) { 
            html = 'No messages for user (id:' + user_id + '), chat: ' + chat.name + ' (id: ' + chat.chatId + ')'; 
            }
      } 
         else {
         html = 'Try to get messages Server response status: ' + xhttp.status + ', response: ' + xhttp.responseText;
         }
   } 
      else {
         html = 'Try to get chat info Server response status: ' + xhttp.status + ', response: ' + xhttp.responseText;
      }
   document.getElementById("chat_frame").innerHTML = html;
   document.getElementById("debug_frame").innerHTML = debug;
}
