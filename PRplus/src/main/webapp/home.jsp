<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="java.io.IOException"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.parser.JSONParser"%>
<%@ page import="org.json.simple.parser.ParseException"%>
<%@ page
import="pr.smash.Dependencies.Methods, pr.smash.Dependencies.Player, pr.smash.Dependencies.Match, pr.smash.Dependencies.Tournament, pr.smash.Dependencies.TournamentPlacings, pr.smash.Dependencies.PlayerRanking, pr.smash.Dependencies.smashGGPlayer, pr.smash.Dependencies.SortablePlayerList"%>


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>PR+</title>

<!-- Bootstrap -->
<link
href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
rel="stylesheet"
integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
crossorigin="anonymous">

<link rel="stylesheet" href="animate.css">
<link rel="stylesheet" href="custom.css">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
</head>

<script async
src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<script>
(adsbygoogle = window.adsbygoogle || []).push({
google_ad_client: "ca-pub-3877923746483160",
enable_page_level_ads: true
});
</script>

<%
Methods method = new Methods();
ArrayList<Player> manualTournamentResults = new ArrayList<Player>();
ArrayList<Player> players = method.getSessionPlayers(session);
%>
<link rel="shortcut icon" type="image/x-icon" href="favIcon.ico" />
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
<div class="container">
<div class="navbar-header">
    <button type="button" class="navbar-toggle collapsed"
            data-toggle="collapse" data-target="#navbar" aria-expanded="false"
            aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span> <span
                                                             class="icon-bar"></span> <span class="icon-bar"></span> <span
class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="#">PR+</a>
</div>
<div id="navbar" class="collapse navbar-collapse">
    <ul class="nav navbar-nav">
    <li class="active"><a href="home.jsp">Home</a></li>
					<li><a href="FAQ.jsp"  class="active">FAQ</a></li>
					<li><a href="contact.jsp">Contact</a></li>
					<li><a href="tournaments.jsp">Tournaments</a></li>
    </ul>
</div>
<!--/.nav-collapse -->
</div>
</nav>
<!--input-->
<div class="container">
<div class="row">
<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 ">
    <div class="panel-group animated fadeInUp" id="fixed">
        <div class="panel panel-danger" id="importSelect">
            <div class="panel-heading accordion-toggle collapsed"
                 data-toggle="collapse" data-parent="#fixed" data-target="#load">
                <h4 class="panel-title">Load Data</h4>
            </div>
            <div id="load" class="panel-collapse collapse">
                <div class="panel-body" style="color: black;">
                    
                        <h4>Load from file</h4>
                        <form method="POST" enctype="multipart/form-data"
                              action="ImportFromFile">
                            <p>
                                <input type="file" name="importFile">
                            </p>
                            <p>
                                <button type="submit" class="btn btn-sm" style="color: black;">Press
                                    to upload the file!</button>
                            </p>
                        </form>
                        <hr>
                     
                            <form method="POST" action="ImportFromUrl">
                                <h4>Load from URL</h4>
                                <input type="text" name="importUrl" id="url"
                                       placeholder="http://challonge.com/u7vaxfqp" >
                                <button type="submit" id="submitURL" class="btn btn-sm" style="color: black;">Load
                                    From URL</button>
									<div class="form-group" style="display:none;" id="game-wrapper" >
										<p>Would you like to include all pools?</p>
										<div class="btn-group" data-toggle="buttons">
                                        <label class="btn btn-default active"> 
                                        <input type="radio" name='radio' checked value="exclude"> 
                                         <span>Exclude Pools</span>
                                        </label> 
                                        <label class="btn btn-default"> 
                                        <input type="radio"  name='radio' value="include"> 
                                        <span>Include Pools</span>
                                        </label>
                                    </div>
									</div>
								</form>
<script>
    onload = function () {
       var url = document.getElementById('url');
       url.oninput = CreateSmashGGParams;
       url.onpropertychange = url.oninput; // for IE8
       // e.onchange = e.oninput; // FF needs this in <select><option>...
       // other things for onload()
    };
</script>
								<script>
function CreateSmashGGParams()
{
	 var url = document.getElementById('url');
	 var val = url.value;
	 var game_wrapper = document.getElementById('game-wrapper');
	 if(val.indexOf("smash.gg") !== -1)
		 game_wrapper.setAttribute("style", "display:block");
	 else
		 game_wrapper.setAttribute("style", "display:none");
};
</script>
							

								<!-- <form method="POST" action="ManualMatch">
                                <h4>Manually Enter new Match</h4>
                                <input type="text" name="winner" placeholder="Winner">
                                <input type="text" name="loser" placeholder="Loser"> <input
                                                                                            type="text" name="wScore" placeholder="Winner's Score">
                                <input type="text" name="lScore" placeholder="Loser's Score">
                                <input type="text" name="event" placeholder="Event">
                                <p>
                                    <button type="submit" class="btn btn-sm" style="color: black;">Enter</button>
                                </p>

                            </form>
                            <form method="POST" action="ManualTournament">
                                <h4>Manually Enter new Tournament</h4>
                                <input type="text" name="event" placeholder="Event">
                                <p>
                                    <button type="submit" class="btn btn-sm" style="color: black;">Enter</button>
                                </p>

                            </form> -->
                            </div>
                        </div>
                </div>
                <%   if(players.size() != 0){%>
                    <div class="panel panel-danger" id="playerSelect">
                        <div class="panel-heading accordion-toggle collapsed"
                             data-toggle="collapse" data-parent="#fixed" data-target="#search">
                            <h4 class="panel-title">Search</h4>
                        </div>
                        <div id="search" class="panel-collapse collapse">
                            <div class="panel-body" style="color: black;">
                                <h4>Search for Player</h4>
                                <form method="POST" action="LoadPlayerData">
                                    <input type="text" name="playerA" placeholder="Mang0"
                                           style="color: black;"> <input type="text"
                                                                         name="playerB" placeholder="Armada (Optional)"
                                                                         style="color: black;">
                                    <button type="submit" class="btn btn-sm" style="color: black;">Search</button>
                                </form>

                               
                                    <h4>Search for Tournament</h4>
                                    <form method="POST" action="LoadTournamentData">
                                        <input type="text" name="tournamentName"
                                               placeholder="genesis-3">
                                        <button type="submit"
                                                class="btn btn-sm black-background white">Search</button>
                                    </form>
                                    <h4>Show Excluded Matches</h4>
                                    <form method="POST" action="LoadExcludedMatches">
                                        <input type="text" name="player"
                                               placeholder="Player to Search for (Optional)">
                                        <button type="submit"
                                                class="btn btn-sm black-background white">Search</button>
                                    </form>
                                     <h4>Show Excluded Placings</h4>
                                    <form method="POST" action="LoadExcludedPlacings">
                                        <input type="text" name="player"
                                               placeholder="Player to Search for (Optional)">
                                        <button type="submit"
                                                class="btn btn-sm black-background white">Search</button>
                                    </form>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-danger">
                        <div class="panel-heading accordion-toggle collapsed"
                             data-toggle="collapse" data-parent="#fixed" data-target="#merge">
                            <h4 class="panel-title" style="color: white;">Modify Data</h4>
                        </div>
                        <div id="merge" class="panel-collapse collapse">
                            <div class="panel-body" style="color: black;">
                                <form method="POST" action="MergePlayers">
                                    <h4>Merge PlayerA into PlayerB</h4>
                                    <input type="text" name="playerA" placeholder="PlayerA">
                                    <input type="text" name="playerB" placeholder="PlayerB">
                                    <p>
                                        <button type="submit" class="btn btn-sm" style="color: black;">Merge</button>
                                    </p>
                                </form>
                                <form method="POST" action="RenamePlayer">
                                    <h4>Rename Player</h4>
                                    <input type="text" name="playerA" placeholder="Old Name">
                                    <input type="text" name="playerB" placeholder="New Name">
                                    <p>
                                        <button type="submit" class="btn btn-sm" style="color: black;">Rename</button>
                                    </p>
                                </form>
                                <form method="POST" action="ExcludeMatch">
                                    <h4>Exclude/Include Match</h4>
                                    <input type="text" name="winner" placeholder="Winner">
                                    <input type="text" name="loser" placeholder="Loser"> <input
                                                                                                type="text" name="wScore" placeholder="Winner's Score">
                                    <input type="text" name="lScore" placeholder="Loser's Score">
                                    <input type="text" name="event" placeholder="Event">


                                    <div class="btn-group" data-toggle="buttons">
                                        <label class="btn btn-default active"> <input type="radio" name='radio' checked value="exclude"> 
                                        <span>Exclude</span>
                                        </label> <label class="btn btn-default"> 
                                        <input type="radio"  name='radio' value="include"> <span> Include</span>
                                        </label>
                                    </div>



                                    <p>
                                        <button type="submit" class="btn btn-sm" style="color: black;">Enter</button>
                                    </p>

                                </form>
                                <form method="POST" action="ExcludePlacing">
                                    <h4>Exclude/Include Placing</h4>
                                    <input type="text" name="player" placeholder="Mango"> <input
                                                                                                 type="text" name="event" placeholder="HTC Throwdown">
                                    <p>
                                    <div class="btn-group" data-toggle="buttons">
                                        <label class="btn btn-default active"> <input
                                                                                      type="radio" name='radio' checked value="exclude"> <span>
                                            Exclude</span>
                                        </label> <label class="btn btn-default"> <input type="radio"
                                                                                        name='radio' value="include"> <span> Include</span>
                                        </label>
                                    </div>

                                    <button type="submit" class="btn btn-sm" style="color: black;">Enter</button>

                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-danger" id="downloadSelect">
			                        <div class="panel-heading" style="color: black;">
			                            <!-- <form method="POST" action="ExportToFile">
			                                <input type="text" name="fileName" placeholder="FileName">
			                                <button type="submit" class="btn btn-sm black-background white">Save
			                                    Bracket Data</button>
			                            </form> -->
							<a href="ExportToFile" target="_blank" style="color:white;">Save Data (Right click and Save As...)</a>
                        </div>
                    </div>
                    <div class="panel panel-danger">
                        <div class="panel-heading accordion-toggle collapsed"
                             data-toggle="collapse" data-parent="#fixed" data-target="#undo">
                            <h4 class="panel-title" style="color: white;">Undo</h4>
                        </div>
                        <div id="undo" class="panel-collapse collapse">
                            <div class="panel-body" style="color: black;">
                            <%if(session.getAttribute("canUndo").equals("true")){ %>
                                <form method="POST" action="UndoImport">
                                    <h4>This will undo your last import from URL</h4>
                                    <button type="submit" class="btn btn-sm" style="color: black;">Undo Last Import</button>
                                </form>
                                <%}else{%>
                                	 <h4>You can no longer undo last URL import</h4>
                                <%}%>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-danger">
                        <div class="panel-heading accordion-toggle collapsed"
                             data-toggle="collapse" data-parent="#fixed" data-target="#clear">
                            <h4 class="panel-title" style="color: white;">Clear All Data</h4>
                        </div>
                        <div id="clear" class="panel-collapse collapse">
                            <div class="panel-body" style="color: black;">
                                <form method="POST" action="Clear">
                                    <h4>This will clear all data.</h4>
                                    <button type="submit" class="btn btn-sm" style="color: black;">Clear Data</button>
                                </form>
                            </div>
                        </div>
                    </div>
                    <%}%>
                        </div>
                    </div>



                <!-- players -->

                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 pull right">
                    <%if(players.size() > 0){%>
                        <br>
                        <form method="post" action="Sort">
                            <input type="text" name="sorts" placeholder="5 (Number of Sorts)">
                            <button type="submit" class="btn btn-default" style="color: black;">Sort
                                PR</button>
                        </form>


                        <%}%>
                            <% if(request.getAttribute("displayData") != null){ 
                               String[] playerData = ((String) request.getAttribute("playerData")).split("\n");%>

                                <div id="dataPanel" class="panel panel-default animated fadeInUp">
                                    <div class="panel-heading" data-toggle="collapse" data-target="#searchedPlayer">
                                        <h4 class="panel-title">
                                            <a class="accordion-toggle" ><%= playerData[0]%></a>
                                                </h4>
                                            </div>
                                        <div id="searchedPlayer" class="panel-collapse collapse">
                                            <div class="panel-body">
                                                <%for(int i = 1; i < playerData.length; i++){%>
                                                <h5>
                                                    <%= playerData[i]%></h5>
                                                    <%}%>
                                                        </div>

                                                    </div>
                                            </div>
                                            <% }%>
                                                <div class="panel-group animated fadeInUp" id="absolute">

                                                    <% for(int i = 0; i < players.size(); i++){%>


                                                    <div class="panel panel-default hoverable">
                                                        <div class="panel-heading" data-toggle="collapse" data-parent="#fixed" data-target="#collapse<%=i%>" >
                                                            <h4 class="panel-title">
                                                                <a class="accordion-toggle"> <% Player player = players.get(i);%>
                                                                    <%= player.getName() %>
                                                                        </a>
                                                                    </h4>
                                                                    </div>
                                                                <div id="collapse<%=i%>" class="panel-collapse collapse">
                                                                    <div class="panel-body">
                                                                        <%  for(int j = 0; j < player.getPlacings().size(); j++){
                                                                        TournamentPlacings placing = player.getPlacings().get(j);%>
                                                                        <p>
                                                                            Placed
                                                                            <%= placing.getPlacing()%>
                                                                                at
                                                                                <%= placing.getTournament()%></p>
                                                                                <% }%>
                                                                                    </div>
                                                                                </div>
                                                                    </div>

                                                                    <%   }%>


                                                                        </div>
                                                                </div>

                                                                <img alt="Logo" src="logo.png" id="logo">

                                                                <script
                                                                        src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
                                                                <script
                                                                        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
                                                                        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
                                                                        crossorigin="anonymous"></script>
                                                                <script>
                                                                    $(function(){
                                                                        $('a.button').on('click',function(){
                                                                            $('input[name=stuff]').addClass('animated slideInUp').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function() {
                                                                                $(this).removeClass('animated slideInUp');
                                                                            });

                                                                        });
                                                                    });
                                                                </script>
                                                                </div>
                                                        </div>

                                                        </body>

                                                    </html>
