<?php
$cadena = filter_input(INPUT_POST, 'txtCadena');
$numAlfabeto = filter_input(INPUT_POST, 'txtAlfabeto');
$numEstado = filter_input(INPUT_POST, 'txtEstado');
$estados = array();

for($i=0; $i<$numEstado; $i++){
    $fila = array();
    for($j=0; $j<$numAlfabeto; $j++){
        $nameState = filter_input(INPUT_POST, 'selectState' . $i.$j);
        array_push($fila, $nameState);
    }
    array_push($estados, $fila);
}

print_r($estados);
echo '<br/>';
$expresion = "";
$validaCadena = str_split($cadena);
$resultado = false;
$estadoActual = "";
$countValidaCadena = count($validaCadena);
echo $countValidaCadena;
echo '<br/>';

$estadoFinal = "";
for($filaEstado=0; $filaEstado<$numEstado; $filaEstado++){
    $filaEstado1 = $estados[$filaEstado];
    $conteo = count($filaEstado1) - 1;
    if(substr($filaEstado1[$conteo], 2) == 'OK'){
        $estadoFinal = substr($filaEstado1[$conteo], 0, 2);
    }
}

if($estadoFinal == ''){
    echo "No ha definido el estado final del autómata";
    return;
}
echo 'Estado Final '.$estadoFinal.';<br/>';
$cortarCiclo=false;
$rowActual=0;

for($l=0; $l<$countValidaCadena; $l++){
    $caracterCadena = $validaCadena[$l];
    echo 'Caracter que valido ' . $caracterCadena;
    echo '<br/>';
    for($j=$rowActual; $j<$numEstado; $j++){
        $filaaux = $estados[$j];
        $conteo = count($filaaux) - 1;

        for($k=0; $k<$conteo; $k++){
            $caracter = substr($filaaux[$k], 2);
            $nombreEstado = substr($filaaux[$k], 0, 2);
            echo 'Estado al que pasaria ' .$nombreEstado .' Letra a validar '. $caracter;
            echo '<br/>';
            if($caracter == 'E'){
                continue;
            }
            if($caracter == $caracterCadena){
                $k = $conteo;
                $cortarCiclo = true;
                
                if($l==($countValidaCadena-1) AND $nombreEstado == $estadoFinal){
                    $k = $conteo;
                    $rowActual = $numEstado;
                    $resultado = true;
                }
                $rowActual = intval(substr($nombreEstado, 1, 1)) -2;
                echo 'Pasa al estado ' . $nombreEstado . '<br/>';
            }
        }
        $rowActual++;
        if($cortarCiclo){
            break;
        }
    }
}

if($resultado){
    echo 'Cadena Válida';
}else{
    echo 'Cadena No Válida';
}