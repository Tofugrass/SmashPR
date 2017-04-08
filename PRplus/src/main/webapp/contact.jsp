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
					<li><a href="home.jsp">Home</a></li>
					<li><a href="#about">About</a></li>
					<li class="active"><a href="contact.jsp">Contact</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>
	<!--input-->
	<div class="container">
	
	<br><br><br><br><br>
	<h4>ABOUT WHAT MY GUY</h4>
	</div>


	<img alt="Logo" src="logo.png"
		style="position: fixed; left: 0px; bottom: 0px; margin-bottom: 50px; margin-left: 50px;">

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
