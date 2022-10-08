
function getChatInfo(id) {
   var xhttp = new XMLHttpRequest();
   var request = host_chats + '/' + id;
   xhttp.open('GET', request, true);
   xhttp.onreadystatechange = function () {
      if (xhttp.readyState != 4) return;
      let html = '';
      if (xhttp.status == 200) {
         g_currentChat = JSON.parse(xhttp.responseText);  
         html = '<center><font color="#0000FF"<b>' + g_currentChat.description;
         html += '    (' + g_currentChat.chatType + ')</b></font></center>';
      }	else {
         g_currentChat == null;
         html = '<br>Error. ' + xhttp.responseText;
      }
      setInfoDiv(html);
   }
   xhttp.send();
}

function loc_getUserDtoById(id) {
   for (let i = 0; i < g_currentChat.users.length; i++) {
      if (g_currentChat.users[i].id == id) {
         let userDto = '{ "id":"' + g_currentChat.users[i].id + '"';
         userDto += ', "name":"' + g_currentChat.users[i].name + '"';
         userDto += ', "avatarId":"' + g_currentChat.users[i].avatarId + '"';
         userDto += ', "avatarName":"' + g_currentChat.users[i].avatarName + '"';
         userDto += '}';
         return JSON.parse(userDto);
      }
   }
   return JSON.parse('{ "name":"unknown user" }');
}

function loc_showStatusesByMessageId(id) {
   let res = 'No statuses for this message.';
   for (let j = 0; j < g_messageList.length; j++) {
      if (g_messageList[j].id == id) {
         if (g_messageList[j].statuses != null) {
            res = '';
            for (let i = 0; i < g_messageList[j].statuses.length; i++) {
               let user = loc_getUserDtoById(g_messageList[j].statuses[i].userId); 
               res += user.name + ' - ';
               res += g_messageList[j].statuses[i].status + '\n';
            }
         }
      }
   }
   alert('Statuses for message (id:' + id + ')\n' + res);
}

function editMessage(id) {
   let e_message;
   for (let i = 0; i < g_messageList.length; i++) {
      if (g_messageList[i].id == id) {
         e_message = g_messageList[i];
         break;
      }
   }
   if (e_message == null) {
      alert('Message for edit not found?!');
         document.getElementById("edit_div").innerHTML = addFormCreateMessage();
      return;
   }
   addFormEditMessage(e_message);
}

function getMessageStatusByUserId(user_id, statuses) {
	if (statuses == null) {
      return 'no status';
   }
   for (let i = 0; i < statuses.length; i++) {
		if (statuses[i].userId == user_id) {
			return statuses[i].status;
		}
   }
}

function createDivMessage(user_id, message) {
   let class_info = user_id == message.userId ? 'td_author_info' : 'td_mes_info';
   let class_text = user_id == message.userId ? 'td_author_text' : 'td_mes_text';
   let res =  '';
   let user = loc_getUserDtoById(message.userId);
   res +='<td class="' + class_info + '">';
// add User name
   res += '<b>' + user.name + '</b>';
// add Avatar (if exists)
   if (user.avatarName != null && user.avatarName != 'null' && user.avatarName != '') {
      res += '<br><br>' + user.avatarName; 
   }   
   res += '</td>';
   res += '<td class="' + class_text + '">';
   res += '<font size="-1"><i>Posted at: ' + message.dateTime + '&nbsp;&nbsp;&nbsp;&nbsp;';
   res += '(' + getMessageStatusByUserId(user_id, message.statuses) + ')</i></font>&nbsp;&nbsp;&nbsp;&nbsp;';
// add See statuses button   
   res += '<button onclick="loc_showStatusesByMessageId(' + message.id + ')">See statuses</button>';
// add Edit button (for Author only)
   if (user_id == message.userId) {
      res += '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="editMessage(' + message.id + ')">Edit message</button>';
   }   
   res += '<p>' + message.messageText + '</p>';
// add Attachment (if exists)
   if (message.fileId != null) {
      res += '<font size="-1">attached file: "' + message.fileName + '" (id:' + message.fileId + ', ';
      res += message.fileType + ')</font>';
   }
   res += '</td>';
   return res;
}

function saveNewMessage() {
   let file_name = '';
   if (document.getElementById("attached_file").files.length > 0) {
         file_name = document.getElementById("attached_file").files[0].name;
   }
   let savedMessage = common_createMessage(g_currentChat.id, g_currentUser.id, file_name, 
				document.getElementById("message_text").value); 
   if (savedMessage.error != null) {   
      alert(savedMessage.error);
   }
   showChatMessages(g_currentChat.id, g_currentUser.id, g_currentUser.name);
}

function saveEditedMessage() {
   let file_name = '';
   if (document.getElementById("attached_file").files.length > 0) {
         file_name = document.getElementById("attached_file").files[0].name;
   }
   let message_id = parseInt(document.getElementById("e_mes_id").innerHTML);
   let file_id = -1;
   if (document.getElementById("e_file_id").innerHTML != '') {
      file_id = parseInt(document.getElementById("e_file_id").innerHTML);
   }
   let editedMessage = common_saveEditedMessage(g_currentChat.id, g_currentUser.id, 
      message_id, file_id, file_name, document.getElementById("message_text").value); 
   if (editedMessage.error != null) {   
      alert(editedMessage.error);
      cancelEdit(); 
   } else {
      document.getElementById("messageId" + editedMessage.id).innerHTML = createDivMessage(g_currentUser.id, editedMessage);
   }
}

function cancelEdit() {
   document.getElementById("edit_div").innerHTML = addFormCreateMessage();
}

function addFormEditMessage(message) {
   let res = '<form action="#"><fieldset class="fieldset1"><legend>Edit message:</legend>';
   res += '<label class="label1" for="message_text" >Message:</label>';
   res += '<label id = "e_mes_id" hidden="true">' + message.id + '</label>';
   res += '<input id="message_text" size=65><br>';
   res += '<label class="label1" for="attached_file" >Attached file:</label>';
   res += '<label id = "e_file_id" hidden="true"></label>';
   res += '<input id="attached_file" type="file"><br>'
   res += '<button onclick="saveEditedMessage()">Save edited message</button>&nbsp;&nbsp;&nbsp;&nbsp;';
   res += '<button onclick="cancelEdit()">Cancel</button>&nbsp;&nbsp;&nbsp;&nbsp;';
   res += '</fieldset></form>'
   document.getElementById("edit_div").innerHTML = res;
   document.getElementById("message_text").value = message.messageText;
   if (message.fileId != null) { 
      document.getElementById("e_file_id").innerHTML = message.fileId;
   }
//   let for_file = document.getElementById("attached_file");
   if (message.fileName != null && message.fileName != '') { 
//    fake File object !!!! to add fileName only
      let myFile = new File(['Hello World!'], message.fileName, 
         {type: 'text/plain', lastModified: new Date(), });
         let dataTransfer = new DataTransfer();
         dataTransfer.items.add(myFile);
         document.getElementById("attached_file").files = dataTransfer.files;
   }
}

function addFormCreateMessage() {
   let res = '<form action="#"><fieldset class="fieldset1">';
   res += '<legend>Reply (create new message):</legend>';
   res += '<label class="label1" for="message_text"><font size="-1">Your message:</font></label>';
   res += '<input id="message_text" size=65 placeholder="Message text (up to 255 chars)"><br>';
   res += '<label class="label1" for="attached_file"><font size="-1">Attach file:</font></label>';
   res += '<input id="attached_file" type="file"><br>'
   res += '<button onclick="saveNewMessage()">';
   res += 'Save message</button>&nbsp;&nbsp;&nbsp;&nbsp;';
   res += '</fieldset></form>'
   return res;
}

function showChatMessages(chat_id, user_id, user_name) {
   let xhttp = new XMLHttpRequest();
   request = host_messages + "/chat/" + chat_id + "/user/" + user_id;
   xhttp.open('GET', request , true);
   let result = '<table class="table_message">';
   xhttp.onreadystatechange = function () {
      if (xhttp.readyState != 4) return;
      if (xhttp.status == 200) {
         g_messageList = JSON.parse(xhttp.responseText);
         for (let i = 0; i < g_messageList.length; i++) {
            result += '<tr id="messageId' + g_messageList[i].id + '">';
            result += createDivMessage(user_id, g_messageList[i]);
            result += '</tr>';
         }
         result += '<tr><td> </td><td class="td_create"><div id="edit_div">';
         result += addFormCreateMessage(g_currentChat.id, g_currentUser.id) + '</div></td></tr>';
         result += '</table>';
         if (g_messageList.length == 0) { 
            result = 'No messages for user "' + user_name + '" in this chat.'; 
         }
      } 
         else {
         result = '<br>Error. ' + xhttp.responseText;
      }
      setChatDiv(result);
   } 
   xhttp.send();
}
