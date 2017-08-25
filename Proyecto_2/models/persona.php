<?php

class personas_model{
    private $db;
    private $personas;
 
    public function __construct(){
        $this->db=Conectar::conexion();
        $this->personas=array();
    }
    public function get_personas(){
        $consulta=$this->db->query("select agenda_id as correlativo, nombres, apellidos, num_telefono as telefono from agenda;");
        $i = 0;
        while($filas=$consulta->fetch_assoc()){
            $this->personas[$i]=$filas;
            $i++;
        }
        return $this->personas;
    }
}
?>