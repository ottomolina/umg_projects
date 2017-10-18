<?php
//Llamada al modelo
require_once("models/persona.php");
$per=new personas_model();
$datos=$per->get_personas();
 
//Llamada a la vista
require_once("view/view.php");
?>