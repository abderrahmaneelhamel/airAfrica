<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500&display=swap" rel="stylesheet">
</head>
<body>
<div class="main">
    <div id="logo">
        <h2>Air Africa</h2>
    </div>

    <input type="checkbox" id="chk" aria-hidden="true">

    <div class="signup">
        <form action="register" method="post">
            <label id="chk1" for="chk" aria-hidden="true">Sign up</label>
            <input type="text" name="username" placeholder="User name" required="">
            <input type="email" name="email" placeholder="Email" required="">
            <input type="password" name="password" placeholder="Password" required="">
            <button type="submit">Sign up</button>
        </form>
    </div>

    <div class="login">
        <form action="login" method="post">
            <input name="choice" value="login" style="display: none">
            <label for="chk" aria-hidden="true">Login</label>
            <input type="email" name="email" placeholder="Email" required="">
            <input type="password" name="password" placeholder="Password" required="">
            <button type="submit">Login</button>
        </form>
    </div>
</div>
</body>
</html>