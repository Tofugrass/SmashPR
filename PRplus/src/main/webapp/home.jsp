<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="java.io.IOException"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.parser.JSONParser"%>
<%@ page import="org.json.simple.parser.ParseException"%>
<%@ page import="pr.smash.Dependencies.Methods, pr.smash.Dependencies.Player, pr.smash.Dependencies.Match, pr.smash.Dependencies.Tournament, pr.smash.Dependencies.TournamentPlacings, pr.smash.Dependencies.PlayerRanking, pr.smash.Dependencies.smashGGPlayer, pr.smash.Dependencies.SortablePlayerList"%>


    <!DOCTYPE html>
    <html lang="en">

        <head>
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
            <title>PR+</title>

            <!-- Bootstrap -->
            <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

            <link rel="stylesheet" href="animate.css">
            <link rel="stylesheet" href="custom.css">
            <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
            <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
            <!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
        </head>
        <%
           Methods method = new Methods();
           ArrayList<Player> manualTournamentResults = new ArrayList<Player>();
        ArrayList<Player> players = method.getSessionPlayers(session);
        ArrayList<Match> includedMatches = method.getSessionIncludedMatches(session);
        ArrayList<Match> excludedMatches = method.getSessionExcludedMatches(session);
        ArrayList<Tournament> tournaments = method.getSessionTournaments(session);
        ArrayList<TournamentPlacings> includedPlacings = method.getSessionIncludedPlacings(session);
        ArrayList<TournamentPlacings> excludedPlacings = method.getSessionExcludedPlacings(session);

        SortablePlayerList pr = new SortablePlayerList(players, 2);

        %>
        <link rel="shortcut icon" type="image/x-icon" href="favIcon.ico" />
        <body>

            <nav class="navbar navbar-inverse navbar-fixed-top">
                <div class="container">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#">PR+</a>
                    </div>
                    <div id="navbar" class="collapse navbar-collapse">
                        <ul class="nav navbar-nav">
                            <li class="active"><a href="#">Home</a></li>
                            <li><a href="#about">About</a></li>
                            <li><a href="#contact">Contact</a></li>
                        </ul>
                    </div>
                    <!--/.nav-collapse -->
                </div>
            </nav>
            <!--input-->
            <div class="container">
                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-3 col-xs-3 pull-left">
                        <div class="panel-group animated fadeInUp" id="fixed">
                            <div class="panel panel-danger" id="importSelect">
                                <div class="panel-heading accordion-toggle collapsed" data-toggle="collapse" data-parent="#fixed" data-target="#load">
                                    <h4 class="panel-title">
                                        Load Data
                                    </h4>
                                </div>
                                <div id="load" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <form method="POST" action="ImportFromUrl">
                                            <input type="text" name="importUrl" placeholder="http://challonge.com/u7vaxfqp">
                                            <button type="submit" class="btn btn-sm"  style="color:black;">Load From URL</button>
                                        </form>
                                            <%
                                            
                                            if(session.getAttribute("importFromFile") == null || (Boolean) session.getAttribute("importFromFile") || session.getAttribute("importFromFile").equals(null) ){%>
                                            	<h4>Load from file: </h4>
                                            	<form method="POST" enctype="multipart/form-data" action="ImportFromFile">
                                            	<p> <input type="file" name="importFile">  </p>
                     <!-- <label class="btn btn-default btn-file"> Browse  <input
				type="file" style="display: none;" name="importFile" >
				</label> -->
                                                <p> <button type="submit" class="btn btn-sm"  style="color:black;">Press to upload the file!</button>
                                                 </p>
                                                </form>
                                                <%} %>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-danger" id="playerSelect">
                                <div class="panel-heading accordion-toggle collapsed" data-toggle="collapse" data-parent="#fixed" data-target="#player">
                                    <h4 class="panel-title">
                                        Player Search
                                    </h4>
                                </div>
                                <div id="player" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <form method="POST" action="LoadPlayerData">
                                            <input type="text" name="playerA" placeholder="Mang0">
                                            <input type="text" name="playerB" placeholder="Armada (Optional)">
                                            <button type="submit" class="btn btn-sm"  style="color:black;">Search</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-danger">
                                <div class="panel-heading accordion-toggle collapsed" data-toggle="collapse" data-parent="#fixed" data-target="#merge">
                                    <h4 class="panel-title" style="color:white;" >
                                        Modify Data
                                    </h4>
                                </div>
                                <div id="merge" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <form method="POST" action="MergePlayers">
                                        <h4>Merge PlayerA into PlayerB</h4>
                                            <input type="text" name="playerA" placeholder="PlayerA">
                                            <input type="text" name="playerB" placeholder="PlayerB">
                                            <p><button type="submit" class="btn btn-sm" style="color:black;">Merge</button></p>
                                        </form>
                                        <form method="POST" action="RenamePlayer">
                                        <h4>Rename Player</h4>
                                            <input type="text" name="playerA" placeholder="Old Name">
                                            <input type="text" name="playerB" placeholder="New Name">
                                           <p> <button type="submit" class="btn btn-sm"  style="color:black;">Rename</button></p>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-danger" id="downloadSelect">
                                <div class="panel-heading" style="color: black;">
                                        <form method="POST" action="ExportToFile">
                                            <input type="text" name="fileName" placeholder="FileName">
                                            <button type="submit" class="btn btn-sm black-background white">Save Bracket Data</button>
                                        </form>                                    
                                   
                                </div>
                            </div>
                            <div class="panel panel-danger" id="tournamentSelect">
                                <div class="panel-heading"  style="color: black;">

                                        <form method="POST" action="LoadTournamentData">
                                            <input type="text" name="tournamentName" placeholder="genesis-3">
                                            <button type="submit" class="btn btn-sm black-background white">Search for Tournament</button>
                                        </form>                                    
                                    
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- players -->

                    <div class="col-lg-6 col-md-6 col-sm-3 col-xs-3 pull right">

                        <% if(request.getAttribute("displayData") != null){ 
                           String[] playerData = ((String) request.getAttribute("playerData")).split("\n");%>

                            <div id="dataPanel" class="panel panel-default animated fadeInUp">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-target="#searchedPlayer"><%= playerData[0]%></a>
                                            </h4>
                                        </div>
                                    <div id="searchedPlayer" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <%for(int i = 1; i < playerData.length; i++){%>
                                            <h5> <%= playerData[i]%></h5>
                                                <%}%>
                                                    </div>

                                                </div>
                                        </div>
                                        <% }%>
                                            <div class="panel-group animated fadeInUp" id="absolute">

                                                <% for(int i = 0; i < players.size(); i++){%>


                                                <div class="panel panel-default hoverable">
                                                    <div class="panel-heading">
                                                        <h4 class="panel-title">
                                                            <a data-toggle="collapse" data-parent="#fixed" data-target="#collapse<%=i%>">
                                                                <% Player player = players.get(i);%>
                                                                    <%= player.getName() %>
                                                                        </a>
                                                                    </h4>
                                                                </div>
                                                            <div id="collapse<%=i%>" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <%  for(int j = 0; j < player.getPlacings().size(); j++){
                                                                    TournamentPlacings placing = player.getPlacings().get(j);%>
                                                                    <p>Placed <%= placing.getPlacing()%> at <%= placing.getTournament()%></p>
                                                                        <% }%>
                                                                            </div>
                                                                        </div>
                                                                </div>

                                                                <%   }%>


                                                                    </div>
                                                            </div>
                                                           
<img alt="Logo" src="logo.png" style="position:fixed; left: 0px; bottom: 0px; margin-bottom: 50px; margin-left: 50px;">

                                                            <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
                                                            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
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
