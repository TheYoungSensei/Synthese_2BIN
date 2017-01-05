$(function(){
	$("#envoiCalc").click(function(e){
		location.href = "/Calc?expr=" + encodeURIComponent($('textarea').val());
	})
})
