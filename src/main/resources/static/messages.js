var table_header = '<table><tr><th>messageId</th><th>chatId</th><th>userId</th>' + 
		   '<th>fileId</th><th>dateTime</th><th width="400">messageText</th></tr>';
var table_footer = '</table>';

function _fillMessageRow(message) {
   res = '<tr><td>' + message.id + '</td><td>' + message.chatId + '</td><td>' + message.userId;
	res += '</td><td>' + message.fileId +'</td><td>' + message.dateTime + '</td><td>' + message.messageText;
	res += '</td></tr>';
	return res;
}

function messages_getMessageById(message_id) {
   var xhttp = new XMLHttpRequest();
   var request = host_messages + '/' + message_id;
   var debug = '[debug] Request: ' + request;
   xhttp.open('GET', request, false);
   xhttp.send();
   debug += '<br>[debug] Response status: ' + xhttp.status;
   var message = null;
   if (xhttp.status == 200 && xhttp.responseText != '') {
      message = JSON.parse(xhttp.responseText);  
      debug += '<br>[debug] Response: ' + xhttp.responseText;
      console.log(message);
   }
   document.getElementById("debug_frame").innerHTML = debug;
   return message;
}   

function messages_findById(message_id) {
   message = messages_getMessageById(message_id);
   if (message == null) {
      return 'message id:' + message_id + ' not found';
   }
   return table_header + _fillMessageRow(message) + table_footer;
}

function messages_findAllByChatId(chat_id) {
   var xhttp = new XMLHttpRequest();
   var request = host_messages + '/' + chat_id + '/chat';
   var debug = '[debug] Request: ' + request;
   xhttp.open('GET', request , false);
   xhttp.send();
   debug = debug + '<br>[debug] Response status: ' + xhttp.status;
   if (xhttp.status == 200) {
   debug += '<br>[debug] Response: ' + xhttp.responseText;
      var messages = JSON.parse(xhttp.responseText);  
      var html = table_header;
      for (var i = 0; i < messages.length; i++) {
         var message = messages[i];
         console.log(message);
         html += _fillMessageRow(message);
      }
		html += table_footer;	
      if (messages.length == 0) {
         html = 'No messages on a Chat Id: ' + chat_id;
      }
   } else { html = 'Error'; }	
   document.getElementById("debug_frame").innerHTML = debug;
   return html;
}

function messages_deleteById(id, user_id) {
   if (!confirm ('Are you sure to delete message id:' + id + '?')) { return 'Canceled'; } 
   var xhttp = new XMLHttpRequest();
   var request = host_messages + '/' + id + '/user/' + user_id;
   var debug = '[debug] Request: ' + request;
   xhttp.open("DELETE", request, false);
   xhttp.send();
   debug += '<br>[debug] Response status: ' + xhttp.status;
   document.getElementById("debug_frame").innerHTML = debug;
   return ('Delete Status: ' + xhttp.status);
}

function messages_create(chat_id, user_id, file_name, m_text) {
   if (!confirm ('Are you sure to create message "' + m_text + '"?')) { return 'Canceled'; } 
   file_id = common_saveFile(file_name);
//
//    TODO - перевірити та дописати
//
   var xhttp = new XMLHttpRequest();   // new HttpRequest instance
   xhttp.open("POST", host_messages);
   xhttp.setRequestHeader("Content-Type", "application/json");
   xhttp.send(JSON.stringify({id: 0, chatId: chat_id, userId: user_id, messageText: m_text, fileId: file_id, dateTime: timestamp(new Date())}));
   xhttp.onreadystatechange = function () {
      if (xhttp.readyState == 4 && xhttp.status == 200) {
         var new_message = JSON.parse(xhttp.responseText);  
         console.log(new_message);
         return messages_findById(new_message.id);
      } else { return 'POST Status: ' + xhttp.status;  }
   }
}

function messages_getUsersList(id) {
   var xhttp = new XMLHttpRequest();
   var request = host_chats_users + '/' + id + '/chat';
   var debug = '[debug] Request: ' + request;
   xhttp.open('GET', request , false);
   xhttp.send();
   debug = debug + '<br>[debug] Response status: ' + xhttp.status;
 	var result = new Array;
   if (xhttp.status == 200) {
      debug += '<br>[debug] Response: ' + xhttp.responseText;
      var users = JSON.parse(xhttp.responseText); 
      for (var i = 0; i < users.length; i++) {
         var elem = new Array(2);
         elem[0] = users[i].id;
         elem[1] = users[i].name;
         result.push(elem);
      }
   }
   console.log(result);
	return result;
}	  

