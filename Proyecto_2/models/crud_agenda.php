<?php

require_once("../connection/db.php");

$accion = filter_input(INPUT_POST, 'accion');

if($accion == "insert"){
    insertaAgenda();
}else if($accion == "update"){
    actualizaAgenda();
}else if($accion == "delete"){
    eliminaContacto();
}

function insertaAgenda(){
    $nombre = filter_input(INPUT_POST, 'nombre');
    $apellido = filter_input(INPUT_POST, 'apellido');
    $telefono = filter_input(INPUT_POST, 'telefono');
    $db=Conectar::conexion();
    
    $sql = "INSERT INTO AGENDA(AGENDA_ID, NOMBRES, APELLIDOS, NUM_TELEFONO) "
            . "select ifnull(max(agenda_id), 0) + 1, '".$nombre."', '".$apellido."', ".$telefono." from agenda;";
    
    $db->query($sql);
    
    echo "El registro se ha guardado exitosamente.";
}


function actualizaAgenda(){
    $nombre = filter_input(INPUT_POST, 'nombre');
    $apellido = filter_input(INPUT_POST, 'apellido');
    $telefono = filter_input(INPUT_POST, 'telefono');
    $agenda_id = filter_input(INPUT_POST, 'agenda_id');
    $db=Conectar::conexion();
    
    $sql = "UPDATE AGENDA SET NOMBRES = '".$nombre."', APELLIDOS = '".$apellido."', NUM_TELEFONO = ".$telefono." "
            . "WHERE AGENDA_ID = ".$agenda_id.";";
    
    $db->query($sql);
    
    echo "El registro se ha modificado exitosamente.";
}

function eliminaContacto(){
    
    $agenda_id = filter_input(INPUT_POST, 'agenda_id');
    
    $db = Conectar::conexion();
    
    $sql = "delete from agenda where agenda_id = ".$agenda_id.";";
    $db->query($sql);
    
    echo "El registro se ha eliminado.";
}

?>
