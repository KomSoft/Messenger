//	var host = 'http://localhost:8080/messages';
var host_file = 'http://localhost:8080/file';

function timestamp(date) {
   if (date == null) { date = new Date(); }
   var date_time = date.getFullYear() + '-' + (date.getMonth() < 9 ? "0" : "") + (date.getMonth()+1) + '-';
       date_time = date_time + (date.getDate() < 10 ? "0" : "") + date.getDate() + 'T';
       date_time = date_time + (date.getHours() < 10 ? "0" : "") + date.getHours() + ':';
       date_time = date_time + (date.getMinutes() < 10 ? "0" : "") + date.getMinutes() + ':';
       date_time = date_time + (date.getSeconds() < 10 ? "0" : "") + date.getSeconds();
   return date_time;
}

function _checkType(_ext, types) {
	res = false;
	for (i = 0; i < types.length; i++) {
		if (_ext == types[i]) { return true; }
	}
}

function _extractExtension(file_name) {
	return file_name.match(/\.([^.]+)$|$/)[1];
}

function _getFileType(file_name) {
	const sound = ['wav', 'mp3'];
	const image = ['jpg', 'jpeg', 'bmp', 'gif', 'png'];
	const office = ['doc', 'docx', 'xls', 'xlsx', 'rtf'];
	const adobe = ['pdf'];
	var file_ext = _extractExtension(file_name);
	fType = 'unknown';
   if (checkExt(file_ext, sound)) { fType = 'sound'; } 
   if (checkExt(file_ext, image)) { fType = 'image'; } 
   if (checkExt(file_ext, office)) { fType = 'msoffice_document'; } 
   if (checkExt(file_ext, adobe)) { fType = 'pdf_document'; } 
}

function common_saveFile(file_name) {
	fType = _getFileType(file_name);
   var xhttp = new XMLHttpRequest();
   xhttp.open("POST", host_file, false);
   xhttp.setRequestHeader("Content-Type", "application/json");
   xhttp.send(JSON.stringify({id: 0, file: file_name, fileType: fType}));
console.log('[common_saveFile] Response readyState: ' + xhttp.readyState + ', xhttp.status:' + xhttp.status);
//   xhttp.onreadystatechange = function () {
console.log('[common_saveFile] Response readyState: ' + xhttp.readyState + ', xhttp.status:' + xhttp.status);
//		if (xhttp.readyState == 4 && xhttp.status == 200) {
		if (xhttp.status == 200) {
         var new_file = JSON.parse(xhttp.responseText);  
         console.log(new_file);
         return new_file.id;
      } else { 
			console.log('[common_saveFile] Response status:' + xhttp.status);
			return null;  
		}
//	}
}
