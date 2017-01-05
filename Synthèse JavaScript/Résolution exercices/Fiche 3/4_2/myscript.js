$(function(){
	$('input, textarea, select, button').css('background-color', '#FAFAD2');
	$('#image').css('width', '300px');
	$('textarea[name="commentaire"').click(function(e){
		$(this).val(' ');
	});
	$('form').on("focus", "input", function(e){
		$(this).css('background-color', '#F0E68C');
	});
	$('form').on("blur", "input", function(e){
		$(this).css('background-color', '#FAFAD2');
	});
	$('#image').click(function(e){
		alert("Sexe : " + $('input[name="sexe"]:checked').val());
	});
	$('input[name="sexe"]').val(['H']);
	$('input').first().focus();
});