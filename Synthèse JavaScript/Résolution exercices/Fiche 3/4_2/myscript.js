$(function(){
	$('input, textarea, select, button').css('background-color','#FAFAD2');
	$('input[name="sexe"]').val(['H']);
	$('#image').css('width','300px');
	$('textarea[name="commentaire"]').click(function(e){
		$(this).val(" ");
	});
	$('form').on("focus", "input", function(e){
		$(this).css('background-color','#F0E68C');
	})
	$('form').on("blur", "input", function(e){
		$(this).css('background-color', '#FAFAD2');
	});
	$('form input').first().focus();
	$("#image").click(function(){
		alert("Sexe : " + $('input[name="sexe"]:checked').val())
	})
});
