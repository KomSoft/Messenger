var chat_table_header = '<table><tr><th width=50>id</th><th width=100>name</th><th width=150>description</th>' + 
							'<th width=100>chatType</th><th width=100>action</th></tr>';
var chat_table_footer = '</table>';

function chats_getChatTypes() {
   var xhttp = new XMLHttpRequest();
   var request = host_chats + '/types';
   var debug = '[debug] Request: ' + request;
   xhttp.open('GET', request, false);
   xhttp.send();
   debug += '<br>[debug] Response status: ' + xhttp.status;
   if (xhttp.status == 200) {
      var types = JSON.parse(xhttp.responseText);
      var result = '';      
      debug += '<br>[debug] Response: ' + xhttp.responseText;
      for (var i = 0; i < types.length; i++) {
         result += '<option value="' + i + '">' + types[i] + '</option>';
      }
	}
   document.getElementById("debug_frame").innerHTML = debug;
	return result;
}

function fillChatRow(chat, lastColText) {
   if (lastColText == null || lastColText == '') { lastColText = '&nbsp;'; }
   res = '<tr><td>' + chat.id + '</td><td>' + chat.name + '</td><td>' + chat.description;
	res += '</td><td>' + chat.chatType + '</td>';
	res += '<td>' + lastColText + '</td></tr>';
	res += '</tr>';
   return res;
}

function getDeleteButton(chat_id) {
   if (chat_id == null || chat_id < 0) { return '&nbsp;'; }
	return '<button onclick="chats_deleteChat(' + chat_id + ')">Delete</button>';
}

function chats_findById(id) {
   var xhttp = new XMLHttpRequest();
   var request = host_chats + '/' + id;
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
   var xhttp = new XMLHttpRequest();
   var request = host_chats;
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
   if (!confirm ('Are you sure to delete chat (id:' + id + ')?')) { 
      document.getElementById("chat_list").innerHTML = ' ';
      return 'Canceled'; 
   } 
   var xhttp = new XMLHttpRequest();
   var request = host_chats + '/' + id;
   var debug = '[debug] Request: ' + request;
   xhttp.open("DELETE", request, true);
   xhttp.send();
   debug += '<br>[debug] Response status: ' + xhttp.status;
   document.getElementById("debug_frame").innerHTML = debug;
   return ('Delete Status: ' + xhttp.status);
}
	
function chats_createChat(name, description, chatType, userId) {
   var xhttp = new XMLHttpRequest();   // new HttpRequest instance
   xhttp.open("POST", host_chats);
   xhttp.setRequestHeader("Content-Type", "application/json");
   xhttp.send(JSON.stringify({id:0, name: name, description: description, chatType: chatType}));
   xhttp.onreadystatechange = function () {
      if (xhttp.readyState == 4 && xhttp.status == 200) {
         var new_chat = JSON.parse(xhttp.responseText);  
         console.log(new_chat);
         return chat_table_header + fillChatRow(new_chat, getDeleteButton(new_chat.id)) + chat_table_footer;
      } else { return 'POST Status: ' + xhttp.status;  }
   }
}
