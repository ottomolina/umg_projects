<!DOCTYPE html>
<html>
    <head>
        <title>Agenda de Contactos</title>
		<link rel="stylesheet" href="css/alertify.core.css">
		<link rel="stylesheet" href="css/alertify.default.css">
        <link rel="stylesheet" href="css/estilo_1.css" id="linkStyle">
        <link rel="stylesheet" href="css/bootstrap.min.css">
		<script src="javascript/alertify.js"></script>
        <script src="javascript/jquery.min.js"></script>
        <script src="javascript/function.js"></script>
        <script src="javascript/bootstrap.min.js"></script>
    </head>
    <body class="body-princ">
        <header class="header">
            <h2>Web ABC</h2>
        </header>
        <div class="container">
            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#datos" id="btnAgregar">Agregar</button>
<!--            <button type="button" class="btn btn-default" id="btnCambiarEstilo">Change Style</button>-->
            
            <table class="table tableData">
                <thead>
                <tr>
                    <th>Correlativo</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Teléfono</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
            <?php
                foreach ($datos as $dato) {
                    echo "<tr class=\"fila\">";
                    echo "<td>" . $dato["correlativo"] . "</td>";
                    echo "<td>" . $dato["nombres"] . "</td>";
                    echo "<td>" . $dato["apellidos"] . "</td>";
                    echo "<td>" . $dato["telefono"] . "</td>";
                    echo "<td class=\"btnModificar\" data-toggle=\"modal\" data-target=\"#datos\">Modificar</td>";
                    echo "<td class=\"btnEliminar\" data-toggle=\"modal\" data-target=\"#confirm\">Eliminar</td>";
                    echo "</tr>";
                }
            ?>
            </table>

            <div class="modal fade" id="datos" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content" style="width: 450px;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title"></h4>
                        </div>
                        <div class="modal-body" style="align-content: center">
                            <table class="tblForm" style="width: 100%; text-align: center">
                                <tr>
                                    <td>Nombres</td><td><input type="text" id="txtNombre" name="txtNombre" maxlength="128"/>
                                </tr>
                                <tr>
                                    <td>Apellidos</td><td><input type="text" id="txtApellido" name="txtApellido" maxlength="128"/>
                                </tr>
                                <tr>
                                    <td>Teléfono</td><td><input type="number" id="intTelefono" name="intTelefono" max="99999999"/>
                                </tr>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" data-dismiss="modal" id="btnGuardar">Guardar</button>
                            <button type="button" class="btn btn-warning" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>
            
            <div id="confirm" class="modal fade" role="dialog">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Confirmar acción</h4>
                        </div>
                        <div class="modal-body">
                             <h5>Realmente desea eliminar el registro?</h5>
                        </div>
                        <div class="modal-footer" style="text-align: center;">
                            <a href="#" data-dismiss="modal" class="btn btn-success onYesDelete">Si</a>
                            <a href="#" data-dismiss="modal" class="btn btn-warning onNoDelete">No</a>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
        
        <footer class="footer">
            <div class="row">
                <div class="col-sm-2" style="text-align: right;">
                    <p>Developed By</p>
                </div>
                <div class="col-sm-2" style="text-align: left;">
                    <p>
                        Ottoniel Molina<br>
                        Nextor Bonilla
                    </p>
                </div>
                <div class="col-sm-8" style="text-align: right;">
                    <p style="bottom: 0">
                        &copy; 2017 Proyecto Universitario sin fines de lucro
                    </p>
                </div>
            </div>
        </footer>
        
    </body>
</html>