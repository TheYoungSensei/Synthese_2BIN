$(function() {
	$('#btnNewCol').click(function(e){
		addCol($('#valeurCellule').val());
	});
	$('#btnNewRow').click(function(e){
		addRow($('#valeurCellule').val());
	});
	$('#myTable').on("click", "td", function(){
		changeValue($(this));
	});
	$('#myTable').on("contextmenu", "td", function(event){
		if(event.altKey === true) {
			$(this).css('background-color','white');
		} else {
			$(this).css('background-color','yellow');
		}
	});
});

function addCol(val){
	$('tr').each(function(index){
		$(this).append('<td>' + val + '</td>');
	});
}

function addRow(val){
	var tr = '<tr>';
	$('tr:last td').each(function(index){
		tr += '<td>' + val + '</td>';
	});
	tr += '</tr>';
	$('tr:last').after(tr);
}

function changeValue($td){
	$('#valeurCellule').val($td.text());
	var $oldVal = $td.text();
	var $input = $('<input id="maVal"/>');
	$input.css('text-align','center');
	$input.val($oldVal);
	$td.html($input);
	$input.focus();
	$input.keyup(function(event){
		if(event.which === 13) { //ENTER
			$td.html($input.val());
			$('#valeurCellule').val($input.val());
		} else if (event.which === 27) {
			$td.html($oldVal);
		} else if (event.altKey === true){
			var key = event.which;
			console.log(key);
			switch(key){
				case 38: //Haut
					$td.html($oldVal);
					changeValue($td.parent().prev().children().eq($td.index()));
					break;
				case 39: //Droite
					$td.html($oldVal);
					changeValue($td.next());
					break;
				case 40: //Bas
					$td.html($oldVal);
					changeValue($td.parent().next().children().eq($td.index()));
					break;
				case 37:
					$td.html($oldVal);
					changeValue($td.prev());
					break;

			}
		}
	});
	$input.blur(function(event){
		$td.html($oldVal);
	});
}


