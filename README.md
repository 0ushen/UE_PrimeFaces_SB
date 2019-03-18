# UE_PrimeFaces_SB
Database Management System using PrimeFaces and JPA

<h3 class="text-center">Introduction</h3>
<p>This app is a user interface which can manage our school database.</p>
<p>More specifically, it can search, add, update or delete :</p>
<ul>
	<li>A person (be it a student, a teacher or school staff).</li>
	<li>A section.</li>
	<li>A teaching unit (or "unité d'enseignement" in french -> UE).</li>
	<li>A capacity belonging to a teaching unit.</li>
	<li>An organized UE which represent the program of a teaching unit for a an academic year.</li>
	<li>A planning item which means a session of a teaching unit.</li>
</ul>
<br/>

<p>It was developped using the following technologies : </p>
<ul>
	<li> 
		<i><a href="https://www.primefaces.org/" target="_blank" class="link-color">PrimeFaces</a></i> (JSF Framework) with the  
		<i><a href="https://github.com/adminfaces/admin-template" target="_blank" class="link-color">Admin Template</a></i>  to create the user interface.
	</li>
	<li>JAVA as the main programming language, with a few frameworks :
		<ul>
			<li>JSF/PrimeFaces for UI components and client-server communication.</li>
			<li>JPA to manage data from the database.</li>
			<li><i><a href="https://jersey.github.io/" target="_blank" class="link-color">Jersey</a></i>  to build the API.</li>
		</ul>
	</li>
	<li>
		<i><a href="https://swagger.io/" target="_blank" class="link-color">Swagger</a></i>  to generate the API documentation (<i><a href="https://github.com/swagger-api/swagger-core/wiki/swagger-core-jersey-1.x-project-setup-1.5" target="_blank" class="link-color">Swagger core</a></i>) and to display it on this website (<i><a href="https://swagger.io/tools/swagger-ui/" target="_blank" class="link-color">Swagger UI</a></i>) .
	</li>
	<li>PostgreSQL as the database management system.</li>
	<li>Netbeans for the development environment.</li>
</ul>
