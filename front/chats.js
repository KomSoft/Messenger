var host = 'http://localhost:8080/chats';
var chat_table_header = '<table><tr><th width=50>id</th><th width=100>name</th><th width=150>description</th>' + 
							'<th width=100>chatType</th><th width=100>action</th></tr>';
var chat_table_footer = '</table>';


//		заглушка, поки немає API
function chats_getChatTypes() {
//   var xhttp = new XMLHttpRequest();
//   var request = host + '/' + id;
//   var debug = '[debug] Request: ' + request;
//   xhttp.open('GET', request, false);
//   xhttp.send();
//   debug += '<br>[debug] Response status: ' + xhttp.status;
//   if (xhttp.status == 200) {
//      var user = JSON.parse(xhttp.responseText);  
//      debug += '<br>[debug] Response: ' + xhttp.responseText;
//      console.log(user);
//      var html = table_header + fillUserRow(user, setDeleteButton(id)) + table_footer;
//   }	else {
//		   html = 'Error. Response status: ' + xhttp.status;
//	}
//   document.getElementById("debug_frame").innerHTML = debug;
//   return html;
	var result = '<option value="1">PERSONAL_CHAT</option>';
	result += '<option value="2">GROUP_CHAT</option>';
	result += '<option value="3">CHANNEL</option>';
	return result;
}

function fillChatRow(chat, lastColText) {
   if (lastColText == null && lastColText == '') { lastColText = '&nbsp;'; }
   res = '<tr><td>' + chat.chatId + '</td><td>' + chat.name + '</td><td>' + chat.description;
	res += '</td><td>' + chat.chatType + '</td>';
	res += '<td>' + lastColText + '</td></tr>';
	res += '</tr>';
   return res;
}

function getDeleteButton(id) {
   if (id == null && id < 0) { return '&nbsp;'; }
	return '<button onclick="chats_deleteChat(' + id + ')">Delete</button>';
}

function chats_findById(id) {
   var xhttp = new XMLHttpRequest();
   var request = host + '/' + id;
   var debug = '[debug] Request: ' + request;
   xhttp.open('GET', request, false);
   xhttp.send();
   debug += '<br>[debug] Response status: ' + xhttp.status;
   if (xhttp.status == 200) {
      var chat = JSON.parse(xhttp.responseText);  
      debug += '<br>[debug] Response: ' + xhttp.responseText;
      console.log(chat);
      var html = chat_table_header + fillChatRow(chat, getDeleteButton(id)) + chat_table_footer;
   }	else {
		   html = 'Error. Response status: ' + xhttp.status;
	}
   document.getElementById("debug_frame").innerHTML = debug;
   return html;
}

function chats_getAllChats() {
alert('APUI not exists yet!');	
   var xhttp = new XMLHttpRequest();
   var request = host;
   var debug = '[debug] Request: ' + request;
   xhttp.open('GET', request , false);
   xhttp.send();
   debug += '<br>[debug] Response status: ' + xhttp.status;
   if (xhttp.status == 200) {
      var chats = JSON.parse(xhttp.responseText);
      var html = chat_table_header;
      for (var i = 0; i < chats.length; i++) {
         console.log(chats[i]);
         html += fillChatRow(chats[i], getDeleteButton(chats[i].id));
      }
		html += chat_table_footer;
      }  else {
			html = 'Error. Response status: ' + xhttp.status;
		}
   document.getElementById("debug_frame").innerHTML = debug;
   return html;
}

function chats_deleteChat(id) {
   if (!confirm ('Are you sure to delete chat id:' + id + '?')) { return 'Canceled'; } 
//  var xhttp = new XMLHttpRequest();
//  xhttp.open("DELETE", "http://localhost:8080/users/delete/" + userId, true);
//        xhttp.send();
	html = 'chats_deleteChat(chatId:' + id + ') called. API not exists yet!';
	alert(html);
	return html;
}
	
function chats_createChat(name, description, chatType) {
   if (!confirm ('Save new Chat "' + name + '" (' + description + '), ' + chatType + '?')) { 
	   return 'Canceled'; 
	} 
	html = 'chats_createChat() with: \nname=' + name + '\ndescription=' + description + '\nchatType=' + chatType + '\n\nAPI not exists yet';
   alert(html);
	return html;
}
