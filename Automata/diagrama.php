<!DOCTYPE html>
<?php
$num_estados = filter_input(INPUT_POST, 'intEstados');
$alfabeto = filter_input(INPUT_POST, 'txtAlfabeto');


?>
<html>
    <head>
        <title>Tabla de Estados</title>
        <link rel="stylesheet" type="text/css" href="css/estilo.css" />
    </head>
    <body>
        <form method="post" action="Proceso.php">
            <table class="tableAutomata">
                <?php
//                    $str = preg_split("/[(,|\s)]/", $alfabeto, -1, PREG_SPLIT_NO_EMPTY);[^\w]|[\w]
                    $str = preg_split('/\.|\s|,/', $alfabeto, -1, PREG_SPLIT_NO_EMPTY);
                    echo "<tr>";
                    echo "<th>Estados</th>";
                    $countStr = count($str);

                    for($i=0; $i<$countStr; $i++){
                        echo "<th>" . $str[$i] . "</th>";
                    }
                    echo "<th>FDC</th>";
                    echo "</tr>";
                    
                    for ($j=0; $j<$num_estados; $j++){
                        echo "<tr><td>Q" . ($j+1) . "</td>";
                      
                        for($k=0; $k<$countStr; $k++){
                            echo "<td><select name=\"selectState" . $j.$k . "\"> ";
                            echo "<option value=\"E\">E</option>";
                            for($l=1; $l<=$num_estados; $l++){
                                echo "<option value=\"Q" . ($l) . $str[$k] ."\">Q" . ($l) . "</option>";
                            }
                            echo "</select></td>";
                        }
                        echo "<td><select name=\"selectState" . $j.$k . "\"> ";
                        echo "<option value=\"Q" . ($l-1) . $str[$k] ."E\">E</option>";
                        echo "<option value=\"Q" . ($l-1) . $str[$k] ."OK\">OK</option>";
                        echo "</select></td>";
                        echo "</tr>";
                    }
                    
                    echo "<input type=\"hidden\" value=\"" . ($countStr + 1) . "\" name=\"txtAlfabeto\"/>";
                    echo "<input type=\"hidden\" value=\"" . $num_estados . "\" name=\"txtEstado\"/>";
                ?>
            </table>
            <br />
            <br />
            <br />
            <label for="txtCadena">Ingrese la cadena a validar:</label>
            <input type="text" id="txtCadena" name="txtCadena" />
            <input type="submit" value="Validar"/>
        </form>
    </body>
</html>
