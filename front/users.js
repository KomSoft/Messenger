var table_header = '<table><tr><th width=50>id</th><th width=100>name</th><th width=50>age</th>' + 
      '<th width=100>login</th><th width=100>password</th>' + 
      '<th width=50>avatarId</th><th width=100>avatarFile</th><th width=100>action</th></tr>';
var table_footer = '</table>';

function fillUserRow(user, lastColText) {
   if (lastColText == null && lastColText == '') { lastColText = '&nbsp;'; }
   res = '<tr><td>' + user.id + '</td><td>' + user.name + '</td><td>' + user.age;
	res += '</td><td>' + user.login + '</td><td>' + user.password + '</td><td>' + user.avatarId;
   res += '</td><td>' + user.avatarName + '</td>';
	res += '<td>' + lastColText + '</td></tr>';
   return res;
}

function getDeleteButton(id) {
   if (id == null && id < 0) { return '&nbsp;'; }
	return '<button onclick="users_deleteUser(' + id + ')">Delete</button>';
}

function users_findById(id) {
   var xhttp = new XMLHttpRequest();
   var request = host_users + '/' + id;
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
   var xhttp = new XMLHttpRequest();
   var request = host_users + '/' + id;
   var debug = '[debug] Request: ' + request;
   xhttp.open("DELETE", request, true);
   xhttp.send();
   debug += '<br>[debug] Response status: ' + xhttp.status;
   document.getElementById("debug_frame").innerHTML = debug;
   return ('Delete Status: ' + xhttp.status);
}

function users_getAllUsers() {
   var xhttp = new XMLHttpRequest();
   var request = host_users;
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
   var request = host_users + '/' + login + '/login' ;
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
   var request = host_users + '/' + name + '/name' ;
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

function users_createUser(name, password, photo_file, login, age) {
   photo_id = common_saveFile(photo_file);
   var xhttp = new XMLHttpRequest(); 
	var request = JSON.stringify({id: 0, name: name, password: password, avatarId: photo_id, login: login, age: age});
   xhttp.open("POST", host_users);
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
