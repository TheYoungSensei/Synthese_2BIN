$(function(){
	$('button[name="TOJSON"]').click(function(e){
		$('#result').val(formTOJSON($('form')));
	});
	$('button[name="TOFORM"]').click(function(e){
		$result = $('#result').val();
		if($result !== undefined && $result !== " "){
			JSONTOForm($('form'), $result);
		}
	});
	$('button[name="CLEAR"]').click(function(e){
		clearForm($('form'));
	})
});

var formTOJSON = function(racine){
	var dict = {};
	$(racine).find('input[type="text"], [type="password"], [type="number"], textarea').each(function(index){
		if($(this).val() !== "") {
			dict[$(this).attr('name')] = $(this).val();
		}
	});

	$(racine).find('input[type=radio]:checked').each(function(index){
		if($(this).val() !== undefined) {
			dict[$(this).attr('name')] = $(this).val();
		}
	});

	$(racine).find('input[type=checkbox]').each(function(index){
		if($(this).is(':checked') !== false) {
			dict[$(this).attr('name')] = $(this).is(':checked');
		}
	});

	$(racine).find('select:not([multiple])').each(function(){
		if($(this) !== undefined) {
			dict[$(this).attr('name')] = $(this).val();
		}
	})

	$(racine).find('select[multiple]').each(function(){
		var $tab = [];
		$(this).find('option:selected').each(function(){
			$tab.push($(this).val());
		});
		if($tab.length > 0){
			dict[$(this).attr('name')] = $tab;
		}
	});

	return JSON.stringify(dict, null, 4);
};

var JSONTOForm = function($racine, $result) {
	var $dict = JSON.parse($result);
	$check = $racine.find('input[type="checkbox"]');
	$check.each(function(){
		$(this).prop("checked", false);
	});
	for(var $name in $dict) {
		$elem = $racine.find('input[name="' + $name + '"]');
		if($elem !== undefined) {
			if($elem.attr('type') === "number"){
				$elem.val(parseInt($dict[$name]));
			} else if ($elem.attr('type') === "text" || $elem.attr('type') === "password") {
				$elem.val($dict[$name]);
			} else if ($elem.attr('type') === "radio") {
				$elem.each(function(index){
					if($elem.val() === $dict[$name]){
						$(this).prop('checked', true);
					}
				});
			} else if ($elem.attr('type') === 'checkbox') {
				if($dict[$name]){
					$elem.prop('checked', true);
				} else {
					$elem.prop('checked', false);
				}
			}
		}
		//Cas des select multiples
		$elem = $racine.find('select[multiple], [name="' + $name + '"]');
		if($elem !== undefined){
			$option = $elem.find('option');
			$i = 0;
			$option.each(function(index){
				if($dict[$name][$i] === $(this).text()){
					$(this).prop('selected', true);
					$i++;
				} else {
					$(this).prop('selected', false);
				}
			})
		}
		$elem = $racine.find('textarea[name="' + $name + '"]');
		if($elem !== undefined) {
			$elem.val($dict[$name]);
		}

		$elem = $racine.find('select:not([multiple]), [name="' + $name + '"]');
		if($elem !== undefined) {
			$option = $elem.find('option');
			$option.each(function(index){
				console.log($dict[$name]);
				if($(this).val() === $dict[$name]){
					$(this).prop('selected', true);
				}
			});
		}
	}
};

var clearForm = function($form){
	$input = $form.find('input[type=text], [type=number], [type=password], textarea');
	$input.each(function(){
		$(this).val("");
	});
	$input = $form.find('input[type=radio]:first');
	$input.each(function(){
		$(this).prop("checked", true);
	});
	$input = $form.find('input[type=checkbox]');
	$input.each(function(){
		$(this).prop("checked", false);
	});
	$input = $form.find('select:not([multiple]) option:first');
	$input.each(function(){
		$(this).prop("selected", true);
	});
	$input = $form.find('select[multiple] option');
	$input.each(function(){
		$(this).prop("selected", false);
	});
}