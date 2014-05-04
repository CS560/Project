<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    	<link rel="stylesheet" type="text/css" href="main.css" />
    	<title>Group 6 - KDM</title>
  </head>
  <body>
    <h1>Group 6: KDM</h1>
    <hr />
    <h2>The following REST services are available</h2>
    <h3>GET Methods</h3>
    <div class="example">
      <strong>Index</strong>
      <br>
      <br><a href="http://134.193.136.127:8080/six.group.kdm/rest/solr/index">http://134.193.136.127:8080/six.group.kdm/rest/solr/index</a>
      <br><em>a generic select-all method - should return 10 results by default - a simple top 10 for testing mockups</em>
    </div>
    <br />
    <div class="example">
      <strong>Search</strong>
      <br>
      <br><a href="http://134.193.136.127:8080/six.group.kdm/rest/solr/search">http://134.193.136.127:8080/six.group.kdm/rest/solr/search</a>
      <br><em>a simple search function with a single query parameter, q</em>
      <p>example: http://localhost:8080/six.group.kdm/rest/solr/search?q=james</p>
    </div>
    <h3>POST Methods</h3>
    <div class="example">
      <strong>Update</strong>
      <br>
      <br><a href="http://134.193.136.127:8080/six.group.kdm/rest/solr/update">http://134.193.136.127:8080/six.group.kdm/rest/solr/update</a>
      <br><em>add documents to Solr index</em>
      <p>this can be easily tested with a plugin like <a href="https://addons.mozilla.org/en-US/firefox/addon/http-resource-test/">Firefox Http Resource Test</a></p>
      <p>
        <em>Note: Set Content-Type to application/json under the Headers tab.</em>
        <br><img src="http-resource-test.png">
      </p>
      
    </div>
  </body>
</html> 
