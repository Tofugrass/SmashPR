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
                    <a class="navbar-brand" href="#">Project name</a>
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
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 pull-left">
                    <div class="panel-group animated fadeInUp" id="fixed">
                        <div class="panel panel-default">
                            <div class="panel-heading accordion-toggle collapsed" data-toggle="collapse" data-parent="#fixed" data-target="#load">
                                <h4 class="panel-title">
                                    <form method="POST" action="ImportFromUrl">
                                            <input type="text" name="importUrl" placeholder="http://oxy.challonge.com/SmashSaturdaysSingles1">
                                            <button type="submit" class="btn btn-lg">Import From URL</button>
                                    </form>
                                </h4>
                            </div>
                            <div id="load" class="panel-collapse collapse">
                                <div class="panel-body"><form method="POST" enctype="multipart/form-data" action="fup.cgi">
                                    File to upload: <input type="file" name="upfile"><br/>
                                    Notes about the file: <input type="text" name="note"><br/>
                                    <br/>
                                    <input type="submit" value="Press"> to upload the file!
                                    </form></div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading accordion-toggle collapsed" data-toggle="collapse" data-parent="#fixed" data-target="#player">
                                <h4 class="panel-title">
                                    <form method="POST" action="LoadPlayerData">
                                            <input type="text" name="playerA" placeholder="Mang0">
                                            <button type="submit" class="btn btn-lg">Search</button>
                                    </form>
                                </h4>
                            </div>
                            <div id="player" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <form method="POST" action="LoadPlayerData">
                                        <input type="text" name="playerB" placeholder="Armada">
                                        <button type="submit" class="btn btn-lg"></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#download">
                                        Download</a>
                                </h4>
                            </div>
                            <div id="download" class="panel-collapse collapse">
                                <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
                                    sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
                                    minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
                                    commodo consequat.</div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- players -->
                <div class="container">
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 pull-right">
                            <div class="panel-group animated fadeInUp" id="absolute">
                                <div class="panel panel-default hoverable">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">
                                                Collapsible Group 1</a>
                                        </h4>
                                    </div>
                                    <div id="collapse1" class="panel-collapse collapse">
                                        <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
                                            sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
                                            minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
                                            commodo consequat.</div>
                                    </div>
                                </div>
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">
                                                Collapsible Group 2</a>
                                        </h4>
                                    </div>
                                    <div id="collapse2" class="panel-collapse collapse">
                                        <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
                                            sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
                                            minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
                                            commodo consequat.</div>
                                    </div>
                                </div>
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapse3">
                                                Collapsible Group 3</a>
                                        </h4>
                                    </div>
                                    <div id="collapse3" class="panel-collapse collapse">
                                        <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
                                            sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
                                            minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
                                            commodo consequat.</div>
                                    </div>
                                </div>
                            </div>
                        </div>



                        <style>
                            body{
                                margin: 50px;
                                background: #2980b9;
                                font-family: sans-serif;
                                text-align: left;
                            }
                            a.button{
                                background: #e74c3c;
                                color: white;
                                padding: 20px;
                                display: inline-block;
                                font-size: 50px;
                                border-radius: 10px;
                            }
                        </style>
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
                        </body>

                    </html>