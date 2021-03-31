var PetsView = (function() {
	var dao;
	
	// Referencia a this que permite acceder a las funciones públicas desde las funciones de jQuery.
	var self;
	var person_id;
	var formId = 'pets-form';
	var listId = 'pets-list';
	var formQuery = '#' + formId;
	var listQuery = '#' + listId;
	
	function PetsView(person_id, petDao, formContainerId, listContainerId) {
		dao = petDao;
		person_id = person_id
		self = this;
		
		insertPetForm($('#' + formContainerId));
		insertPetList($('#' + listContainerId));
		
		this.init = function() {
			dao.listPet(person_id,function(pets) {
				$.each(pets, function(key, pet) {
					appendToTable(pet);
				});
			},
			function() {
			    	alert('No has sido posible acceder al listado de pets.');
			});
			
			// La acción por defecto de enviar formulario (submit) se sobreescribe
			// para que el envío sea a través de AJAX
			$(formQuery).submit(function(event) {
				var pet = self.getPetInForm();
				
				if (self.isEditing()) {
					dao.modifyPet(pet,
						function(pet) {
							$('#pet-' + pet.id + ' td.name').text(pet.name);
							$('#pet-' + pet.id + ' td.especie').text(pet.especie);
							self.resetForm();
						},
						showErrorMessage,
						self.enableForm
					);
				} else {
					dao.addPet(pet,
						function(pet) {
							appendToTable(pet);
							self.resetForm();
						},
						showErrorMessage,
						self.enableForm
					);
				}
				
				return false;
			});
			
			$('#btnClear').click(this.resetForm);
		};

		this.getPetInForm = function() {
			var form = $(formQuery);
			return {
				'id': form.find('input[name="id"]').val(),
				'name': form.find('input[name="name"]').val(),
				'especie': form.find('input[name="especie"]').val(),
				'person_id': person_id
			};
		};

		this.getPetInRow = function(id) {
			var row = $('#pet-' + id);

			if (row !== undefined) {
				return {
					'id': id,
					'name': row.find('td.name').text(),
					'especie': row.find('td.especie').text()
				};
			} else {
				return undefined;
			}
		};
		
		this.editPet = function(id) {
			var row = $('#pet-' + id);

			if (row !== undefined) {
				var form = $(formQuery);
				
				form.find('input[name="id"]').val(id);
				form.find('input[name="name"]').val(row.find('td.name').text());
				form.find('input[name="especie"]').val(row.find('td.especie').text());
				
				$('input#btnSubmit').val('Modificar');
			}
		};
		
		this.deletePet = function(id) {
			if (confirm('Está a punto de eliminar a una pet. ¿Está seguro de que desea continuar?')) {
				dao.deletePet(id,
					function() {
						$('tr#pet-' + id).remove();
					},
					showErrorMessage
				);
			}
		};

		this.isEditing = function() {
			return $(formQuery + ' input[name="id"]').val() != "";
		};

		this.disableForm = function() {
			$(formQuery + ' input').prop('disabled', true);
		};

		this.enableForm = function() {
			$(formQuery + ' input').prop('disabled', false);
		};

		this.resetForm = function() {
			$(formQuery)[0].reset();
			$(formQuery + ' input[name="id"]').val('');
			$('#btnSubmit').val('Crear');
		};
	};
	
	var insertPetList = function(parent) {
		parent.append(
			'<table id="' + listId + '" class="table">\
				<thead>\
					<tr class="row">\
						<th class="col-sm-4">Nombre</th>\
						<th class="col-sm-5">Especie</th>\
						<th class="col-sm-3">&nbsp;</th>\
					</tr>\
				</thead>\
				<tbody>\
				</tbody>\
			</table>'
		);
	};

	var insertPetForm = function(parent) {
		parent.append(
			'<form id="' + formId + '" class="mb-5 mb-10">\
				<input name="id" type="hidden" value=""/>\
				<div class="row">\
					<div class="col-sm-4">\
						<input name="name" type="text" value="" placeholder="Nombre" class="form-control" required/>\
					</div>\
					<div class="col-sm-5">\
						<input name="especie" type="text" value="" placeholder="Especie" class="form-control" required/>\
					</div>\
					<div class="col-sm-3">\
						<input id="btnSubmit" type="submit" value="Crear" class="btn btn-primary" />\
						<input id="btnClear" type="reset" value="Limpiar" class="btn" />\
					</div>\
				</div>\
			</form>'
		);
	};

	var createPetRow = function(pet) {
		return '<tr id="pet-'+ pet.id +'" class="row">\
			<td class="name col-sm-4">' + pet.name + '</td>\
			<td class="especie col-sm-5">' + pet.especie + '</td>\
			<td class="col-sm-3">\
				<a class="edit btn btn-primary" href="#">Editar</a>\
				<a class="delete btn btn-warning" href="#">Eliminar</a>\
			</td>\
		</tr>';
	};

	var showErrorMessage = function(jqxhr, textStatus, error) {
		alert(textStatus + ": " + error);
	};

	var addRowListeners = function(pet) {
		$('#pet-' + pet.id + ' a.edit').click(function() {
			self.editPet(pet.id);
		});
		
		$('#pet-' + pet.id + ' a.delete').click(function() {
			self.deletePet(pet.id);
		});
		
		
	};

	var appendToTable = function(pet) {
		$(listQuery + ' > tbody:last')
			.append(createPetRow(pet));
		addRowListeners(pet);
	};
	
	return PetsView;
})();
