var solr = function () { };
solr.ip = "192.168.56.101";
solr.port = "8983";
solr.url = "http://" + solr.ip + ":" + solr.port + "/solr/#/";
solr.toString = function () {
    return solr.url;
};

var docs = new Array();

function printMediaListItem(doc) {
	var html = '';
	html += '<div class="media">';
	html += '<a class="pull-left" href="#">'; 
	html += '<img class="media-object" src="' + doc.imageUrl + '" alt="...">';
	html += '</a>';
	html += '<div class="media-body">';
	html += '<h4 class="media-heading">' + doc.title + '</h4>';
	html += doc.description;
	html += '</div>';
	html += '</div>';
	
	$('#news-feed-list').append(html);
}

function init() {
	var http = 'http://192.168.56.101:8983/solr/newsfeeds_shard1_replica1/select?q=*%3A*&wt=json&indent=true';
	$.ajax({
		url: http,
		dataType: 'jsonp',
		type: "GET",
		success: function (response) {
			docs = response.response.docs;
			for(var i = 0; i < docs.length; i++) {
				var doc = new Object;
				doc.title = docs[i].title[0];
				doc.description = docs[i].description;
				doc.imageUrl ="http://www.cpsc.gov" + docs[i].mediaContent;
				printMediaListItem(doc);
			}
		}
	});
}



$(document).ready(init());