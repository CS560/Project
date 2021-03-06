﻿function clearMediaList() {
	$('#news-feed-list').html('');	
}
function printMediaListItem(doc) {
	var html = '';
	html += '<div class="media">';
	html += '<a class="pull-left" href="#">'; 
	html += '<img class="media-object" src="' + doc.imageUrl + '" alt="..." height="64" width="64">';
	html += '</a>';
	html += '<div class="media-body">';
	html += '<h4 class="media-heading">' + doc.title + '</h4>';
	html += doc.description;
	html += '</div>';
	html += '</div>';
	
	$('#news-feed-list').append(html);
}

function solrSearch() {
	var q = $('#txtSearch').val();
	var url = 'http://localhost:8080/sos/resources/solr/search?q=' + q;
	console.log(url);
	$.ajax({
		url: url,
		dataType: 'jsonp',
		jsonpCallback: "callback",
		jsonp: false,
		type: "GET",
		success: function (response) {
			clearMediaList();
			var docs = response.response.docs;
			for(var i = 0; i < docs.length; i++) {
				var doc = new Object();
				doc.title = docs[i].title[0];
				doc.description = docs[i].description;
				doc.imageUrl ="http://www.cpsc.gov" + docs[i].mediaContent;
				printMediaListItem(doc);
			}
		}
	});
}

function init() {
	var http = 'http://localhost:8080/sos/resources/solr/index/';

	$.ajax({
		url: http,
		dataType: 'jsonp',
		jsonpCallback: "callback",
		jsonp: false,
		type: "GET",
		success: function (response) {
			clearMediaList();
			var docs = response.response.docs;
			for(var i = 0; i < docs.length; i++) {
				var doc = new Object();
				doc.title = docs[i].title[0];
				doc.description = docs[i].description;
				doc.imageUrl ="http://www.cpsc.gov" + docs[i].mediaContent;
				printMediaListItem(doc);
			}
		}
	});
}



$(document).ready(init());