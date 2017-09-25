<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
        <form method="post" action="diagrama.php">
            <table>
                <tr>
                    <td>Ingrese la cantidad de estados</td>
                    <td><input type="number" name="intEstados" id="intEstados" max="9" /></td>
                </tr>
                <tr>
                    <td>Ingrese el alfabeto del autómata</td>
                    <td><textarea rows="5" cols="30" name="txtAlfabeto" id="txtAlfabeto"></textarea></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Siguiente" /></td>
                </tr>
            </table>
        </form>
    </body>
</html>
