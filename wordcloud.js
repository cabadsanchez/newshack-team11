function wordCloud(selector, entities, width) {
	
	var intervalCloud;
	
	var color = "";
	
	var height = (width * 3) / 4;
	
	var svg = d3.select(selector).append("svg")
    	.attr("width", width)
    	.attr("height", height)
    	.append("g")
    	.attr("transform", "translate(" + width/2 + "," + height/2 + ")");
	
	var fill = d3.scale.linear().domain([0, entities.length]).range(["lightgray", "black"]);
	
	cloud(entities);
	
	function cloud(entities) {
		
		if(entities.length != 0) {
			var scale = d3.scale.linear().domain([entities[entities.length - 1].size, entities[0].size]).range([15,80]);
			fill = d3.scale.linear().domain([0, entities.length]).range(["lightgray", "black"]);
		}
		
		d3.layout.cloud().size([width, height])
        	.words(entities)
        	.random(0.5)
        	.padding(5)
        	.rotate(function(d) { return 0; })
        	.font("Roboto Condensed")
        	.fontSize(function(d) { return scale(d.size); })
        	.on("end", draw)
        	.start();
	}
	
	function draw(entities) {
		//Use the 'text' attribute (the word itself) to identity unique elements.
		var cloud = svg.selectAll("g text")
			.data(entities, function(d) { return d.text; });

		//Entering words
		cloud.enter()
			.append("text")
			.style("font-family", "Roboto Condensed")
			.attr("text-anchor", "middle")
			.attr('font-size', 1)
			.text(function(d) { return d.text; });

		//Entering and existing words
		cloud.on("mouseover", function(d) {
				d3.select(this).transition()
					.duration(200)
					.style("font-size", d.size + 4 +"px");
			})
			.on("mouseout", function(d) {
				d3.select(this).transition()
					.duration(200)
					.style("font-size", d.size +"px");
			})
		.transition()
			.duration(600)
			.style("font-size", function(d) { return d.size + "px"; })
			.attr("transform", function(d) {
				return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
			})
			.style("fill-opacity", 1);

		//Exiting words
		cloud.exit()
			.transition()
			.duration(200)
			.style('fill-opacity', 1e-6)
			.attr('font-size', 1)
			.remove();
	}
}
