var host = 'http://localhost:8080';

function mess_getUsersList() {
   var xhttp = new XMLHttpRequest();
   var request = host + "/users";
   var debug = '[debug] Request: ' + request;
   var html = new Array;
   xhttp.open('GET', request , false);
   xhttp.send();
      debug = debug + '<br>[debug] Response status: ' + xhttp.status;
      if (xhttp.status == 200) {
         debug += '<br>[debug] Response: ' + xhttp.responseText;
         var users = JSON.parse(xhttp.responseText);  
         for (var i = 0; i < users.length; i++) {
            html.push([ users[i].id, users[i].name]);
         }   
         console.log(html);
      }
   document.getElementById("debug_frame").innerHTML = debug;
   return html;
}	
	
function mess_getChatListForUser(user_id) {
//	var chatId_list = getChatListByUserId(parseInt(user_id));
//   var html = chatlist_header;
   var debug = '[debug] user_id: ' + user_id;
//   console.log('[getChatListForUser] user_id: ' + user_id + ', chatId_list: ' + chatId_list);
// can be changed when use ChatMembersLinkService.getChatsByUserId(Long userId) will create 
// заглушка, поки немає методу List<Chats> ChatMembersLinkService.getChatsByUserId(Long userId)
   var a = [ ['1', 'Wild World'], ['2', 'ITEA'], ['3', 'Green Energy is our future'], ['4', 'Programmers news'] ];
   var html = new Array;
   switch (user_id) {
      case 1 : { html.push(a[0]); html.push(a[2]); html.push(a[3]); break; }
      case 2 : { html.push(a[1]); html.push(a[2]); html.push(a[3]); break; }
      case 3 : { html.push(a[0]); html.push(a[2]); html.push(a[3]); break; }
      case 4 : { html.push(a[0]); html.push(a[1]); html.push(a[3]); break; }
      case 5 : { html.push(a[0]); html.push(a[3]); break; }
      case 6 : { html.push(a[0]); html.push(a[2]); html.push(a[3]); break; }
   }
//  	var xhttp = new XMLHttpRequest();
//   var request = host + "/chats/" + chatId_list[i];
//      debug += '<br>[debug] Request: ' + request;
//      xhttp.open('GET', request , false);
//      xhttp.send();
//		if (xhttp.status == 200) {
//         var chat = JSON.parse(xhttp.responseText);
//         html += '<option value="' + chat.chatId + '">' + chat.name + '</option>';
//      }
//   }
//	html += chatlist_footer;
   console.log(html);
//	if (chatId_list.length == 0) { html = 'list is empty'; }
//   document.getElementById("select_chat").innerHTML = html;
   return html;
   document.getElementById("debug_frame").innerHTML = debug;
}

function getMessageInfo(id) {
// get message info TODO  - write sevice & request
   alert('Statuses for message Id:' + id + '\n API not exists yet');
}

function editMessage(id) {
// get edit message TODO  - write sevice & request
   alert('Author can edit his message, id:' + id + '\n API not exists yet');
}

function createDivMessage(user_id, message) {
   var res =  '<tr id="messageId' + message.id + '"><td class="';
   var td1 = user_id == message.userId ? 'td_author_info' : 'td_mes_info';
   var td2 = user_id == message.userId ? 'td_author_text' : 'td_mes_text';
   res += td1 + '">userId:' + message.userId + '<br><br>photo</td><td class="' + td2 + '">';
   res += '<b>Posted at: ' + message.dateTime + '</b>&nbsp;&nbsp;&nbsp;&nbsp;';
   res += '<button onclick="getMessageInfo(' + message.id + ' )">Message status</button>';
   if (user_id == message.userId) {
      res += '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="editMessage(' + message.id + ' )">Edit message</button>';
   }   
   res += '<p>' + message.messageText + '</p>';
   if (message.file != null) {
      res += 'attached file: (id=' + message.fileId + '), type=(next time)'; 
   }
   res += '</td></tr>';
   return res;
}

function createMessage(chat_id, user_id) {
      var file_id = null;
// TODO - save file & get Id or change method      
      text = document.getElementById("message_text").value;
      if (text != '') {
          var result = messages_create(chat_id, user_id, file_id, document.getElementById("message_text").value); 
      }
   alert('Save message result:\n' + result + '\nPlease update chat!');
   getChatMessages(chat_id);
}

function addFormCreateMessage(chat_id, user_id) {
   res = '<tr><td></td><td class="td_create"><form action="#"><fieldset class="fieldset1"><legend>Reply (create new message):</legend>';
   res += '<label class="label1" for="message_text" >Your message:</label>';
   res += '<input id="message_text" size=65 placeholder="Message text (up to 255 chars)"><br>';
   res += '<label class="label1" for="file_id" >File Id:</label>';
   res += '<input id="file_id" placeholder="(Long) File Id">TODO - add select file button<br>'
   res += '<button onclick="createMessage('+ chat_id + ',' + user_id + ')">';
   res += 'Save message</button>&nbsp;&nbsp;&nbsp;&nbsp;';
   res += '<button type="reset" value="Reset">Cancel</button></fieldset></form></td></tr>'
   return res;
}

function mess_getChatMessages(chat_id, user_id) {
   var debug = '[debug] userId: ' + user_id + ', chatId: ' + chat_id;
//    get chat info     
   var xhttp = new XMLHttpRequest();
	var request = host + "/chats/" + chat_id;
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
      request = host + "/messages/" + chat_id + "/chat/" + user_id;
      debug += '<br>[debug] Request: ' + request;
      xhttp.open('GET', request , false);
      xhttp.send();
      if (xhttp.status == 200) {
         messages = JSON.parse(xhttp.responseText);
         for (i = 0; i < messages.length; i++) {
            html += createDivMessage(user_id, messages[i]);
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
