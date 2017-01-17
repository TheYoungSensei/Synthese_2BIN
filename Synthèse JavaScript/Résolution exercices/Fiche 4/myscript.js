$(function(){
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
			$(this).css('background-color', 'white');
		} else {
			$(this).css('background-color', 'yellow');
		}
	});
});

var addCol = function(val){
	$('tr').each(function(index){
		$(this).append('<td>' + val + '</td>')
	});
}

var addRow = function(val) {
	var tr = '<tr>';
	$('tr:last td').each(function(index){
		tr += '<td>' + val + '</td>';
	});
	tr += '</tr>';
	$('tr:last').after(tr);
}

var changeValue = function($td) {
	$('#valeurCellule').val($td.text());
	var $oldVal = $td.text();
	var $input = $('<input id="maVal"/>');
	$input.css('text-align', 'center');
	$input.val($oldVal);
	$td.html($input);
	$input.focus();
	$input.keyup(function(event){
		if(event.which === 13) {
			$td.html($input.val());
			$('#valeurCellule').val($input.val())
		} else if (event.which === 27) {
			$td.html($oldVal);
		} else if (event.altKey === true) {
			var key = event.which;
			switch(key) {
				case 38 :
					$td.html($oldVal);
					changeValue($td.parent().prev().children().eq($td.index()));
					break;
				case 39 :
					$td.html($oldVal);
					changeValue($td.next());
					break;
				case 40 :
					$td.html($oldVal);
					changeValue($td.parent().next().children().eq($td.index()));
					break;
				case 37 :
					$td.html($oldVal);
					changeValue($td.prev());
					break;
			}
		}
	});
	$input.blur(function(event){
		$td.html($oldVal);
	})
}
