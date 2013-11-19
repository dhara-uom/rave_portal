<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

    <meta charset="utf-8">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script>
        $(document).ready(function(){
            setTimeout(get_events, 4000);
        });


        function get_events(){
            $.ajax({
                url: 'http://localhost:8080/portal/app/admin/monitorData',
                success: function(data) {
                    $('#test').append(data);
                }
            });
            setTimeout(get_events, 5000);

        }

    </script>

    <style type="text/css">

    </style>
</head>
<body>
<div id="test"></div>
</body>



