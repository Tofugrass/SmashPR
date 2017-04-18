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

		<br>
		<br>
		<br>
		<br>
		<br>
		<h1>FAQ</h1>

		<h3>Why do players receive different placings for the same tournament?</h3>
		<p>We import all of the standings of each group. Usually, this includes 1 or 2 rounds of pools, then a top 64 bracket, and a top 8 bracket. For example, Mango took 2nd at Genesis-3 melee singles. However, he has two placings when you only import top 64. One of them is 5th, which was his placing in the top64 bracket. He made it into top 8 on loser's side, so he was 5th. His other placing was 2nd in the top 8 bracket. You get to see both.</p>
		<h3>How are players sorted? </h3>
		<p>Currently, they are sorted based on matches and placings. Say we need to sort out two players, you and I. The program first looks at all of the players you have played, and all of the players I have played. If any of these players match, i.e. we have found a mutual player, the program analyzes how you have performed against that player. If you beat that player 100% of the time, and I beat that player 50% of the time, then the program awards one 'match point' to you. If it was a tie, we then look at game count. If both of those are a tie, then no point is awarded. The program does this for all players that we have played directly. 
Then, the program does the same thing for placings. Every player you have attended the same event with is stored in a list. If you placed higher than Mango, for example, the one time you have attended the same event, and I did not, then the system awards one 'placing point' to you. The program does this for all placings and matches for all players, and the listed is sorted.</p>
	<h3>How can I enter a tournament manually?</h3>
	<p>You can create a text document in the same format as an exported file.</p>
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
		$(function() {
			$('a.button')
					.on(
							'click',
							function() {
								$('input[name=stuff]')
										.addClass('animated slideInUp')
										.one(
												'webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend',
												function() {
													$(this)
															.removeClass(
																	'animated slideInUp');
												});

							});
		});
	</script>
	</div>
	</div>

</body>

</html>
