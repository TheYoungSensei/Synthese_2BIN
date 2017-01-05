$('li').each(function(index){
	if((index % 2) === 0) {
		$(this).css('color','red');
	} else {
		$(this).css('color','green');
	}
});