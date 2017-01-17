$('li').each(function(index){
	if(index % 2 === 0){
		$(this).css('color','green');
	} else {
		$(this).css('color','red');
	}
});