const host = 'http://localhost:8080/';
const host_chats = host + 'chats';
const host_chats_users = host + 'chatsuserslinks';
const host_file = host + 'file';
const host_messages = host + 'messages';
const host_status = host + 'statusLinks';
const host_users = host + 'users';

function timestamp(date) {
   if (date == null) { date = new Date(); }
   let date_time = date.getFullYear() + '-' + (date.getMonth() < 9 ? "0" : "") + (date.getMonth()+1) + '-';
       date_time = date_time + (date.getDate() < 10 ? "0" : "") + date.getDate() + 'T';
       date_time = date_time + (date.getHours() < 10 ? "0" : "") + date.getHours() + ':';
       date_time = date_time + (date.getMinutes() < 10 ? "0" : "") + date.getMinutes() + ':';
       date_time = date_time + (date.getSeconds() < 10 ? "0" : "") + date.getSeconds();
   return date_time;
}

function writeResult(result, debug) {
   if (result != '') document.getElementById("result_div").innerHTML = result;
   if (debug != '') document.getElementById("debug_div").innerHTML = debug;
}

function common_deleteMessageById(id, user_id) {
   if (!confirm ('Are you sure to delete message id:' + id + '?')) { return 'Canceled'; } 
   var xhttp = new XMLHttpRequest();
   let request = host_messages + '/' + id + '/user/' + user_id;
   let debug = '[debug] Request: ' + request;
   xhttp.open("DELETE", request, false);
   xhttp.send();
   let result = 'status: ' + xhttp.status + '  ' + xhttp.responseText;
   debug += '<br>[debug] Response ' + result;
   writeResult('', debug);
   return ('Delete ' + result);
}

function common_getAllChatsList() {
   var xhttp = new XMLHttpRequest();
   let request = host_chats;
   let debug = '[debug] Request: ' + request;
   xhttp.open('GET', request , false);
   xhttp.send();
   debug = debug + '<br>[debug] Response status: ' + xhttp.status + '   ' + xhttp.responseText;
   if (xhttp.status != 200) { return null; }
   let result = new Array;
   let data = JSON.parse(xhttp.responseText); 
   for (let i = 0; i < data.length; i++) {
      let elem = new Array(2);
      elem[0] = data[i].id;
      elem[1] = data[i].name;
      result.push(elem);
   }
   console.log(result);
   return result;
}

function common_getAllUsersList() {
   var xhttp = new XMLHttpRequest();
   let request = host_users;
   let debug = '[debug] Request: ' + request;
   xhttp.open('GET', request , false);
   xhttp.send();
   debug = debug + '<br>[debug] Response status: ' + xhttp.status + '   ' + xhttp.responseText;
   if (xhttp.status != 200) { return null }
   let result = new Array;
   let data = JSON.parse(xhttp.responseText); 
   for (let i = 0; i < data.length; i++) {
      let elem = new Array(2);
      elem[0] = data[i].id;
      elem[1] = data[i].name;
      result.push(elem);
   }
   console.log(result);
   return result;
}

function common_createMessage(chat_id, user_id, file_name, m_text) {
/*   if (!confirm ('Are you sure to create message "' + m_text + '"?')) {
      return JSON.parse('{"error":"Canceled"}');
   } 
*/
//   file_id = common_saveFile(file_name);
//
//    TODO - перевірити та дописати
//
   var xhttp = new XMLHttpRequest();
   xhttp.open("POST", host_messages, false);
   xhttp.setRequestHeader("Content-Type", "application/json");
   xhttp.send(JSON.stringify({chatId: chat_id, userId: user_id, messageText: m_text, fileName: file_name, dateTime: timestamp(new Date())}));
//   xhttp.onreadystatechange = function () {
 //     if (xhttp.readyState != 4) return;
console.log('[common_createMessage] status:' + xhttp.status);
      if (xhttp.status == 201) {
         let new_message = JSON.parse(xhttp.responseText);  
         console.log(new_message);
         return new_message;
      } else { 
//         var err = '{"error":"Error creating message. Status: ' + xhttp.status + '  ' + xhttp.responseText + '"}';
         let err = '{"error":"Error creating message. Status: ' + xhttp.status + '"}';
         return JSON.parse(err);
      }
 //  }
//   xhttp.send(JSON.stringify({chatId: chat_id, userId: user_id, messageText: m_text, fileName: file_name, dateTime: timestamp(new Date())}));
}

function common_saveEditedMessage(chat_id, user_id, message_id, file_id, file_name, m_text) {
   var xhttp = new XMLHttpRequest();
   let request = host_messages + '/user/' + user_id;
   xhttp.open("POST", request, false);
   xhttp.setRequestHeader("Content-Type", "application/json");
   xhttp.send(JSON.stringify({chatId: chat_id, userId: user_id, id: message_id, messageText: m_text, fileId: file_id, fileName: file_name}));
//   xhttp.onreadystatechange = function () {
 //     if (xhttp.readyState != 4) return;
console.log('[common_saveEditedMessage] status: ' + xhttp.status);
      if (xhttp.status == 200) {
         let new_message = JSON.parse(xhttp.responseText);  
         console.log(new_message);
         return new_message;
      } else { 
//         var err = '{"error":"Error creating message. Status: ' + xhttp.status + '  ' + xhttp.responseText + '"}';
         let err = '{"error":"Error creating message. Status: ' + xhttp.status + '"}';
         return JSON.parse(err);
      }
 //  }
//   xhttp.send(JSON.stringify({chatId: chat_id, userId: user_id, messageText: m_text, fileName: file_name, dateTime: timestamp(new Date())}));
}









/*

function common_saveFile(file_name) {
   var xhttp = new XMLHttpRequest();
   xhttp.open("POST", host_file, false);
   xhttp.setRequestHeader("Content-Type", "application/json");
   xhttp.send(JSON.stringify({id: 0, fileName: file_name, fileType: 'UNKNOWN'}));
console.log('[common_saveFile] Response readyState: ' + xhttp.readyState + ', xhttp.status:' + xhttp.status);
		if (xhttp.status == 200) {
         var new_file = JSON.parse(xhttp.responseText);  
         console.log(new_file);
         return new_file.id;
      } else { 
			console.log('[common_saveFile] Response status:' + xhttp.status);
			return null;  
		}
}

*/