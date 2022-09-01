var host = 'http://localhost:8080/users';
var table_header = '<table><tr><th width=50>id</th><th width=100>name</th><th width=50>age</th><th width=100>login</th>' +
			'<th width=100>password</th><th width=50>photoId</th><th width=100>action</th></tr>';
var table_footer = '</table>';

function fillUserRow(user, lastColText) {
   if (lastColText == null && lastColText == '') { lastColText = '&nbsp;'; }
   res = '<tr><td>' + user.id + '</td><td>' + user.name + '</td><td>' + user.age;
	res += '</td><td>' + user.login + '</td><td>' + user.password + '</td><td>' + user.photoId + '</td>';
	res += '<td>' + lastColText + '</td></tr>';
   return res;
}

function getDeleteButton(id) {
   if (id == null && id < 0) { return '&nbsp;'; }
	return '<button onclick="users_deleteUser(' + id + ')">Delete</button>';
}

function users_findById(id) {
   var xhttp = new XMLHttpRequest();
   var request = host + '/' + id;
   var debug = '[debug] Request: ' + request;
   xhttp.open('GET', request, false);
   xhttp.send();
   debug += '<br>[debug] Response status: ' + xhttp.status;
   if (xhttp.status == 200) {
      var user = JSON.parse(xhttp.responseText);  
      debug += '<br>[debug] Response: ' + xhttp.responseText;
      console.log(user);
      var html = table_header + fillUserRow(user, getDeleteButton(id)) + table_footer;
   }	else {
		   html = 'Error. Response status: ' + xhttp.status;
	}
   document.getElementById("debug_frame").innerHTML = debug;
   return html;
}

function users_deleteUser(id) {
   if (!confirm ('Are you sure to delete user id:' + id + '?')) { return 'Canceled'; } 
//  var xhttp = new XMLHttpRequest();
//  xhttp.open("DELETE", "http://localhost:8080/users/delete/" + userId, true);
//        xhttp.send();
	html = 'users_deleteUser(userId:' + id + ') called. API not exists yet!';
	alert(html);
	return html;
}

function users_getAllUsers() {
   var xhttp = new XMLHttpRequest();
   var request = host;
   var debug = '[debug] Request: ' + request;
   xhttp.open('GET', request , false);
   xhttp.send();
   debug += '<br>[debug] Response status: ' + xhttp.status;
   if (xhttp.status == 200) {
      var users = JSON.parse(xhttp.responseText);
      var html = table_header;
      for (var i = 0; i < users.length; i++) {
         console.log(users[i]);
         html += fillUserRow(users[i], getDeleteButton(users[i].id));
      }
		html += table_footer;
      }  else {
			html = 'Error. Response status: ' + xhttp.status;
		}
   document.getElementById("debug_frame").innerHTML = debug;
   return html;
}

function users_findByLogin(login) {
   var xhttp = new XMLHttpRequest();
   var request = host + '/' + login + '/login' ;
   var debug = '[debug] Request: ' + request;
   xhttp.open('GET', request , false);
   xhttp.send();
   debug += '<br>[debug] Response status: ' + xhttp.status;
   if (xhttp.status == 200) {
      var user = JSON.parse(xhttp.responseText);
      console.log(user);
      var html = table_header + fillUserRow(user, getDeleteButton(user.id)) + table_footer;
      }  else {
			html = 'Error. Response status: ' + xhttp.status;
		}
   document.getElementById("debug_frame").innerHTML = debug;
   return html;
}

function users_findByName(name) {
   var xhttp = new XMLHttpRequest();
   var request = host + '/' + name + '/name' ;
   var debug = '[debug] Request: ' + request;
   xhttp.open('GET', request , false);
   xhttp.send();
   debug += '<br>[debug] Response status: ' + xhttp.status;
   if (xhttp.status == 200) {
      var user = JSON.parse(xhttp.responseText);
      console.log(user);
      var html = table_header + fillUserRow(user, getDeleteButton(user.id)) + table_footer;
      }  else {
			html = 'Error. Response status: ' + xhttp.status;
		}
   document.getElementById("debug_frame").innerHTML = debug;
   return html;
}

function users_createUser(name, password, photo_id, login, age) {
   if (!confirm ('Are you sure to save new user "' + name + '"?')) { return 'Canceled'; } 
   var xhttp = new XMLHttpRequest(); 
	var request = JSON.stringify({id: 0, name: name, password: password, photoId: photo_id, login: login, age: age});
   xhttp.open("POST", host);
   xhttp.setRequestHeader("Content-Type", "application/json");
   xhttp.send(request);
   var debug = '[debug] POST: ' + request;
   xhttp.onreadystatechange = function () {
      if (xhttp.readyState == 4 && xhttp.status == 200) {
         var new_user = JSON.parse(xhttp.responseText);  
         console.log(new_user);
         debug += new_user;
         html = table_header + fillUserRow(new_user, getDeleteButton(new_user.id)) + table_footer;
         document.getElementById("debug_frame").innerHTML = debug;
         return html;
//			users_getAllUsers();
      } else { return 'POST Status: ' + xhttp.status;  }
//                document.getElementById("message_list").innerHTML = '<tr><td colspan="6">POST Status: ' + xhttp.status + '</td></tr>';
   }
	return 'New user creating... Please update list by clicking "Show all users"';
}
