var identificador = "";
var accion = "";


$(document).ready(function() {
    
    $("#btnGuardar").click(function(e) {
        var nombre = document.getElementById('txtNombre').value;
        var apellido = document.getElementById('txtApellido').value;
        var telefono = document.getElementById('intTelefono').value;
        
        if(nombre.trim() === ""){
            alerta('Debe ingresar al menos un nombre.', "");
            e.stopPropagation();
            return;
        }
        
        if(telefono.trim() === ""){
            alerta('El número de teléfono es obligatorio.', "");
            e.stopPropagation();
            return;
        }
        console.log(accion);
        
        var url = "models/crud_agenda.php";
        $.ajax({
            type: "POST",
            url: url,
            data: 'accion='+accion+'&nombre='+nombre+'&apellido='+apellido+'&telefono='+telefono+'&agenda_id='+identificador,
            success: function (data, textStatus, jqXHR) {
                alerta(data, "act");
            }
        });
    });
    
    $(".onYesDelete").click(function(){
        console.log("Entra a eliminar");
        
        var url = "models/crud_agenda.php";
        $.ajax({
           type: "POST",
           url: url,
           data: 'accion=delete&agenda_id='+identificador,
           success: function(data, textStatus, jwXHR) {
               alerta(data, "act");
           }
        });
    });
    
    $("#btnAgregar").click(function(){
        document.getElementById("txtNombre").value = "";
        document.getElementById("txtApellido").value = "";
        document.getElementById("intTelefono").value = "";
        
        accion = "insert";
        $(".modal-title").text("Nueva Información");
    });
    
    $(".btnModificar").click(function() {
        var valores="";
        $(this).parents("tr").find("td").each(function(){
            valores+=$(this).html()+",";
        });
        
        console.log(valores);
        identificador = valores.split(",")[0];
        console.log(identificador);
        
        document.getElementById("txtNombre").value = valores.split(",")[1];
        document.getElementById("txtApellido").value = valores.split(",")[2];
        document.getElementById("intTelefono").value = valores.split(",")[3];
        
        accion = "update";
        $(".modal-title").text("Actualizar Información");
    });
    
     $(".btnEliminar").click(function() {
        var valores="";
        $(this).parents("tr").find("td").each(function(){
            valores+=$(this).html()+",";
        });
        
        console.log(valores);
        identificador = valores.split(",")[0];
        console.log(identificador);
        
        accion = "delete";
    });
    
    $("#btnCambiarEstilo").click(function(){
       var estilo = document.getElementById("linkStyle").href;
       if(estilo.toString().indexOf("estilo_1.css") > -1){
            document.getElementById("linkStyle").href = "css/estilo_2.css";
//           $("#linkStyle").animate({href: "css/estilo_2.css"});
//            sessionStorage.setItem("Css", style);
       }else if(estilo.toString().indexOf("estilo_2.css") > -1){
           document.getElementById("linkStyle").href = "css/estilo_3.css";
       }else{
           document.getElementById("linkStyle").href = "css/estilo_1.css";
       }
    });
    
});

function alerta(menssage, act){
    
    var titulo = $(".header").find("h2").text();
	if(act === ""){
		alertify.alert("<b>"+titulo+"</b><br>"+menssage+"");
	}else{
		alertify.alert("<b>"+titulo+"</b><br>"+menssage+"", function (){
			window.location.reload();
		});
	}
}
