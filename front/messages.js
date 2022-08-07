var host = 'http://localhost:8080';
var table_header = '<tr><th>messageID</th><th>chatID</th><th>userID</th>' + 
		   '<th>fileID</th><th>date-time</th><th width="400">messageText</th></tr>';

  function timestamp(date) {
    if (date == null) {
      date = new Date();  
      }
      var date_time = date.getFullYear() + '-' + (date.getMonth() < 9 ? "0" : "") + (date.getMonth()+1) + '-';
	  date_time = date_time + (date.getDate() < 10 ? "0" : "") + date.getDate() + 'T';
	  date_time = date_time + (date.getHours() < 10 ? "0" : "") + date.getHours() + ':';
	  date_time = date_time + (date.getMinutes() < 10 ? "0" : "") + date.getMinutes() + ':';
	  date_time = date_time + (date.getSeconds() < 10 ? "0" : "") + date.getSeconds();
      return date_time;
  }

  function messages_findById(message_id) {
        var xhttp = new XMLHttpRequest();
	var request = host + '/messages/' + message_id;
    var debug = '[debug] Request: ' + request;
        xhttp.open('GET', request, true);
        xhttp.send();
        xhttp.onreadystatechange = function () {
    debug = debug + '<br>[debug] Response status: ' + xhttp.status;
            if (xhttp.readyState == 4 && xhttp.status == 200) {
                var message = JSON.parse(xhttp.responseText);  
    debug = debug + '<br>[debug] Response status: ' + xhttp.responseText;
                console.log(message);
                var html = table_header + '<tr><td>' + message.id + '</td><td>' + message.chatId + 
		    '</td><td>' + message.userId + '</td><td>' + message.fileId + 
		    '</td><td>' + message.dateTime + '</td><td>' + message.messageText + 
		    '</td></tr>';
                document.getElementById("message_list").innerHTML = html;
//                return html;
            }
    document.getElementById("debug_frame").innerHTML = debug;
        };
    }

  function messages_findAllByChatId(chat_id) {
        var xhttp = new XMLHttpRequest();
	var request = host + '/messages/' + chat_id + '/chat';
    var debug = '[debug] Request: ' + request;
        xhttp.open('GET', request , true);
        xhttp.send();
        xhttp.onreadystatechange = function () {
    debug = debug + '<br>[debug] Response status: ' + xhttp.status;
            if (xhttp.readyState == 4 && xhttp.status == 200) {
    debug = debug + '<br>[debug] Response status: ' + xhttp.responseText;
                var messages = JSON.parse(xhttp.responseText);  
                var html = table_header;
                for (var i = 0; i < messages.length; i++) {
                    var message = messages[i];
                    console.log(message);
                    html = html + '<tr><td>' + message.id + '</td><td>' + message.chatId + 
		    '</td><td>' + message.userId + '</td><td>' + message.fileId + 
		    '</td><td>' + message.dateTime + '</td><td>' + message.messageText + 
		    '</td></tr>';
                }   
                if (messages.length == 0) {
                   html = html + '<tr><td colspan="6">No messages on a Chat Id: ' + chat_id + '</td></tr>';
                }
                document.getElementById("message_list").innerHTML = html;
//                return html;
            }
    document.getElementById("debug_frame").innerHTML = debug;
        };
    }

  function messages_deleteById(message_id) {
        var xhttp = new XMLHttpRequest();
	var request = host + '/messages/' + message_id;
    var debug = '[debug] Request: ' + request;
        xhttp.open("DELETE", request, true);
        xhttp.send();
        xhttp.onreadystatechange = function () {
    debug = debug + '<br>[debug] Response status: ' + xhttp.status;
	   var result = 'Delete Status: ' + xhttp.status;
           document.getElementById("message_list").innerHTML = result;
    document.getElementById("debug_frame").innerHTML = debug;
        };
    }

  function messages_create(chat_id, user_id, file_id, m_text) {
        var xhttp = new XMLHttpRequest();   // new HttpRequest instance
        xhttp.open("POST", host + '/messages');
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(JSON.stringify({id: 0, chatId: chat_id, userId: user_id, messageText: m_text, fileId: file_id, dateTime: timestamp(new Date())}));
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState == 4 && xhttp.status == 200) {
                var new_message = JSON.parse(xhttp.responseText);  
                console.log(new_message);
                messages_findById(new_message.id);
//                document.getElementById("message_list").innerHTML = html;
//                return html;
            } else {
                document.getElementById("message_list").innerHTML = '<tr><td colspan="6">POST Status: ' + xhttp.status + '</td></tr>';
            }
        }
    }
