var host = 'http://localhost:8080/messages';
var table_header = '<table><tr><th>messageId</th><th>chatId</th><th>userId</th>' + 
		   '<th>fileId</th><th>dateTime</th><th width="400">messageText</th></tr>';
var table_footer = '</table>';

function _fillMessageRow(message) {
   res = '<tr><td>' + message.id + '</td><td>' + message.chatId + '</td><td>' + message.userId;
	res += '</td><td>' + message.fileId +'</td><td>' + message.dateTime + '</td><td>' + message.messageText;
	res += '</td></tr>';
	return res;
}

function messages_findById(message_id) {
   var xhttp = new XMLHttpRequest();
   var request = host + '/' + message_id;
   var debug = '[debug] Request: ' + request;
   xhttp.open('GET', request, false);
   xhttp.send();
   debug += '<br>[debug] Response status: ' + xhttp.status;
   if (xhttp.status == 200) {
      var message = JSON.parse(xhttp.responseText);  
      debug += '<br>[debug] Response: ' + xhttp.responseText;
      console.log(message);
      var html = table_header + _fillMessageRow(message) + table_footer; 
   }
   document.getElementById("debug_frame").innerHTML = debug;
   return html;
}

function messages_findAllByChatId(chat_id) {
   var xhttp = new XMLHttpRequest();
   var request = host + '/' + chat_id + '/chat';
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
   var request = host + '/' + id + '/user/' + user_id;
   var debug = '[debug] Request: ' + request;
   xhttp.open("DELETE", request, false);
   xhttp.send();
   debug += '<br>[debug] Response status: ' + xhttp.status;
   document.getElementById("debug_frame").innerHTML = debug;
   return ('Delete Status: ' + xhttp.status);
}

function messages_create(chat_id, user_id, file_name, m_text) {
   if (!confirm ('Are you sure to create message "' + m_text + '"?')) { return 'Canceled'; } 
// save file_name & get file_id   
//alert('temporary file not saved. file_id sets to null. TODO');
//	common_saveFile(file_name)
   file_id = common_saveFile(file_name);
   var xhttp = new XMLHttpRequest();   // new HttpRequest instance
   xhttp.open("POST", host);
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

// заглушка, поки немає методу getAllChats() 
function messages_getChatsList() {
 	var result = new Array;
   var elem = ["1", "Wild World"];
   result.push(elem);
   elem = ["2", "ITEA"];
   result.push(elem);
   elem = ["3", "Green Energy is our future"];
   result.push(elem);
   elem = ["4", "Programmers news"];
   result.push(elem);
   console.log(result);
	return result;
}

// заглушка, поки немає методу List<Chats> ChatMembersLinkService.getUsersByChatId(Long userId)
function messages_getUsersList(id) {
   console.log('messages_getUsersList called id:' + id);
   var a = [ ['1', 'Karina'], ['2', 'Administrator'], ['3', 'Veronika'], ['4', 'Volodymyr'], ['5', 'Дмитро'], ['6', 'Денис'] ];
   var res = new Array;
   switch (id) {
      case 1 : { res.push(a[0]); res.push(a[2]); res.push(a[3]); res.push(a[4]); res.push(a[5]); break; }
      case 2 : { res.push(a[1]); res.push(a[3]); break; }
      case 3 : { res.push(a[0]); res.push(a[1]); res.push(a[2]); res.push(a[5]); break; }
      case 4 : { res.push(a[0]); res.push(a[1]); res.push(a[2]); res.push(a[3]); res.push(a[4]); res.push(a[5]); break; }
   } 
   console.log(res);
   return res;
}	          						
