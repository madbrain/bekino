
var apiUrl = "theater";

$(document).ready(function() {
	var selectElement = $("#kinoSelect");

    $("#datepicker").datepicker();
	$("#datepicker").datepicker("option", "dateFormat", "yy-mm-dd");

    $.ajax({
        url: apiUrl
    }).done(function(data) {
		_.each(data, function(kino) {
			selectElement.append(
				"<option value='"+kino.allocineCode+"'>"+kino.name+"</option>");
		});
    });

	selectElement.change(function (e) {
		fetch(selectElement.val(), $("#datepicker").val())
	});

	$("#datepicker").change(function (e) {
        fetch(selectElement.val(), $("#datepicker").val())
    });

});

function fetch(code, date) {
    if (code != "null") {
        var url = apiUrl + "/" + code;
        if (date != null && date != "") {
            url += "/?date=" + date;
        }
        $.ajax({
            url: url,
        }).done(function(data) {
            draw(data);
        });
    }
}

function draw(cinemaShowTimes) {

var svg = d3.select("#diagram");

svg.selectAll("g").remove();

var nameMargin = 300;
var startTime = 8; // heure
var stopTime = 27; // heure

var range = Math.abs(stopTime - startTime);

function dateToPosition(hours, minutes) {
    if (hours < 8) {
        hours += 24;
    }
	var s = (hours - startTime) * 60 + minutes;
	return Math.floor((1000 - nameMargin) * s / (range * 60));
}

function timeToPosition(time) {
	var elements = time.split(":");
	return dateToPosition(parseInt(elements[0]), parseInt(elements[1]));
}

function lasting(showTime) {
	return timeToPosition(showTime.endTime)
		- timeToPosition(showTime.startTime);
}

var bars = svg.selectAll("g.bar")
	.data(_.range(startTime, stopTime + 1))
	.enter()
	.append("g")
	.attr("class", "bar")
	;

bars
	.append("line")
	.attr("x1", function(d, i) { return nameMargin + Math.floor((1000 - nameMargin) * i / range); })
	.attr("y1", 0)
	.attr("x2", function(d, i) { return nameMargin + Math.floor((1000 - nameMargin) * i / range); })
	.attr("y2", cinemaShowTimes.films.length * 30)
	.attr("stroke", "gray")
	;

bars
	.append("text")
	.attr("x", function(d, i) { return nameMargin + Math.floor((1000 - nameMargin) * i / range); })
	.attr("y", cinemaShowTimes.films.length * 30 + 20)
	.text(function (d) { return d > 24 ? d - 24 : d; })
	;

var films = svg.selectAll("g.film")
	.data(cinemaShowTimes.films)
	.enter()
	.append("g")
	.attr("class", "film")
	;

films
	.append("rect")
	.attr("x", 0)
	.attr("y", function(d, i) { return i * 30; })
	.attr("width", 1000)
	.attr("height", 30)
	.attr("fill", "none")
	.attr("stroke", "gray")
	;

films
	.append("text")
	.attr("x", 0)
	.attr("y", function(d, i) { return i * 30 + 30; })
	.append("tspan")
	.attr("dy", "-0.25em")
	.text(function (d) { return d.title; })
	;

films
	.selectAll("rect.showTime")
	.data(function (d) { return d.showTimes; })
	.enter()
	.append("rect")
	.attr("class", "showTime")
	.attr("x", function(d, i, j) { return nameMargin + timeToPosition(d.startTime); })
	.attr("y", function(d, i, j) { return j * 30 + (30 - 27.5); })
	.attr("width", function(d) { return lasting(d); })
	.attr("height", 25)
	.attr("fill", "url(#rect-gradient)")
	//.attr("fill", "rgb(128, 64, 64)")
	//.attr("stroke", "rgb(64, 0, 0)")
	.attr("filter", "url(#drop-shadow)")
	;

var current = new Date();
var offset = dateToPosition(current.getHours(), current.getMinutes());
svg
	.append("g")
	.attr("class", "time")
	.append("line")
	.attr("x1", function(d) { return nameMargin + offset; })
	.attr("y1", 0)
	.attr("x2", function(d) { return nameMargin + offset; })
	.attr("y2", cinemaShowTimes.films.length * 30)
	.attr("stroke", "red")
	.attr("stroke-width", "2px")
	;

function updateTime() {
	var current = new Date();
	var offset = dateToPosition(current.getHours(), current.getMinutes());
	svg.select("g.time line")
		.attr("x1", function(d) { return nameMargin + offset; })
		.attr("x2", function(d) { return nameMargin + offset; })
	;
}
 
setInterval(function() {
    updateTime();
}, 1000 * 60);

}
