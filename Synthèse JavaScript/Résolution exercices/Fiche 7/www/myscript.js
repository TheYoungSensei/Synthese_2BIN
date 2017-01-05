var machines = {};

$(function(){
	
	
	$.ajax({
		url : '/Machine',
		type : 'POST',
		data : {
			type : "init"
		},
		success : function(reponseTxt){
			machines = {};
			$("#machines").html(" ");
			machines = JSON.parse(reponseTxt);
			$.each(machines, function(key,val){
				$("#machines").append($('<option>' + key + '</option>'));
				$("#machines option:first").click();
			})
		},
		error : function(e){
			console.log(e.message);
		}
	});
	
	$("#machines").on("change", function(e){
		selectMachine($('#machines').find(':selected').val());
	});
	
	$('#effectuerModif').click(function(e){
		var form = formToJSON($("#formulaire"));
		$.ajax({
			url : '/Machine',
			type : 'POST',
			data : {
				type : 'post',
				id : $("#machines").find(':selected').val(),
				json : JSON.stringify(form)
			},
			success : function(reponse){
				machines = {};
				$("#machines").html(" ");
				machines = JSON.parse(reponse);
				$.each(machines, function(key,val){
					$("#machines").append($('<option>' + key + '</option>'));
					$("#machines option:first").click();
				})
			},
			error : function(e){
				console.log(e.message);
			}
		});
	});
});

var selectMachine = function(machine) {
	JSONToForm($('#formulaire'), machines[machine] );
}

var formToJSON = function(racine) {
	var dict = {};
	
	$(racine).find('input[type=number], [type=text], [type=password], textarea').each(function(){
		if($(this).val() !== "")
			dict[$(this).attr('name')] = $(this).val();
	});
	
	$(racine).find('input[type=radio]:checked').each(function(){
		if($(this) !== undefined)
			dict[$(this).attr('name')] = $(this).val();
	});
	
	$(racine).find('input[type=checkbox]').each(function(){
		if($(this).is(':checked') !== false)
			dict[$(this).attr('name')] = $(this).is(':checked');
	});
	
	$(racine).find('select:not([multiple]) option:selected').each(function(){
		if($(this) !== undefined)
			dict[$(this).attr('name')] = $(this).val();
	});
	
	$(racine).find('select[multiple]').each(function(){
			var $tab = [];
			$(this).find('option:selected').each(function(){
				$tab.push($(this).val());
			});
			if($tab.length !== 0)
				dict[$(this).attr('name')] = $tab;
	});
	console.log(dict);
	return dict;
}

var JSONToForm = function($racine, $dict_text) {
	var $dict = JSON.parse($dict_text);
	for(var $name in $dict){
		$elem = $racine.find('input[name=' + $name + ']');
		if($elem !== undefined) {
			if($elem.attr('type') === "number") {
				$elem.val(parseInt($dict[$name]));
			}
			if($elem.attr('type') === "text" || $elem.attr('type') === "password") {
				$elem.val($dict[$name]);
			} 
			if ($elem.attr('type') === "radio") {
				$elem.each(function(){
					if($(this).val() === $dict[$name])
						$(this).prop("checked", true);
				});
			}
			if($elem.attr('type') === "checkbox") {
				if($dict[$name]){
					$elem.prop("checked", true);
				} else {
					$elem.prop("checked", false);
				}
			}
		}
		// Cas des SELECT non multiples
		$elem = $racine.find('select:not([multiple]), [name=' + $name + ']');
		if($elem !== undefined) {
			$option = $elem.find('option');
			$option.each(function(){
				if($(this).text() === $dict[$name]){
					$(this).prop("selected", true);
				}
			});
		}
		//Cas des SELECT multiples
		$elem = $racine.find('select[multiple], [name=' + $name + ']');
		if($elem !== undefined) {
			$option = $elem.find('option');
			$i = 0;
			$option.each(function(){
				if($dict[$name][$i] === $(this).text()){
					$(this).prop("selected", true);
					$i++
				} else {
					$(this).prop("selected", false);
				}
			});
		}
		$elem = $racine.find('textarea[name=' + $name + ']');
		if($elem !== undefined) {
			$elem.val($dict[$name]);
		}
		
	}
}

var clearForm = function($racine){
	//Traitement des inputs
	$input = $racine.find('input[type=text], [type=number], [type=password], textarea');
	$input.each(function(){
		$(this).val("");
	});
	$checkbox = $racine.find('input[type=checkbox]');
	$checkbox.each(function(){
		$(this).prop("checked", false);
	});
	$radio = $racine.find('input[type=radio]:first');
	$radio.each(function(){
		$(this).prop("checked", true);
	});
	$selectNotMultiple = $racine.find('select:not([multiple]) option:first');
	$selectNotMultiple.each(function(){
		$(this).prop("selected", true);
	});
	$selectMultiple = $racine.find('select[multiple] option');
	$selectMultiple.each(function(){
		$(this).prop("selected", false);
	});
}