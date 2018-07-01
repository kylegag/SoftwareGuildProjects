var rocket;
var points;
var draw;
var outerShell;

var startingX = 200.0;
var startingY = 360.0;

var xValues = [];
var yValues = [];
var rotationDegree = [];
var trail = [];

var count = 0;

$(document).ready(function () {
    setBackGround();
    setUpSVG();
    getPoints();
    wireLaunchBtn();
});

function wireLaunchBtn() {
    var launchButton = $('#launchBtn');

    launchButton.click(function (event) {
        launchRocket();
    });
}

function launchRocket() {
    if(0 < count) {
        return;
    } else {
        count = 2;
    }
    
    trail.forEach(function deleteTrail(element) {
        element.hide();
        element.forget();
    });
    
    outerShell.move(startingX, startingY);
    rocket.rotate(-270);
    var xScale = 7.5;
    var yScale = -25;
    var scaledX;
    var scaledY;
    var degree = 0.0;
    rotationDegree.push(-270);
    $.each(points, function (index, item) {
        scaledX = startingX + (item.x * xScale);
        xValues.push(scaledX);
        scaledY = startingY + (item.y * yScale);
        yValues.push(scaledY);

        if (index !== 0) {
            degree = -180 + Math.atan((yValues[index] - yValues[index - 1]) / (xValues[index] - xValues[index - 1])) * (180 / Math.PI);
            rotationDegree.push(degree);
        }
    });
    animateMove(1, points.length);
    animateRotate(1, points.length);
}

function animateMove(currIndex, size) {
    outerShell.animate({duration: '.05s'}).move(xValues[currIndex], yValues[currIndex]).after(function (event) {
        if (currIndex < size - 1) {
            trail.push(draw.circle(3).fill('#000').move(xValues[currIndex] - 5, yValues[currIndex]));
            animateMove(currIndex + 1, size);
        } else {
            count--;
        }
    });
}

function animateRotate(currIndex, size) {
    rocket.animate({duration: '.05s' }).rotate(rotationDegree[currIndex]).after(function (event) {
        if (currIndex < size - 1) {
            animateRotate(currIndex + 1, size);
        } else {
            count--;
        }
    });
}

function setBackGround() {
    var body = $('body');
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/background',
        success: function (item) {
            body.css('background-image', item);
        },
        error: function () {}
    });
}

function getPoints() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/points',
        success: function (itemArray) {
            points = itemArray;
        },
        error: function () {
            console.log("Failure getting points");
        }
    });
}

function setUpSVG() {
    draw = SVG('svg');
    var nested = draw.nested();
    rocket = nested.group();
    outerShell = nested.group();
    outerShell.add(rocket);
    outerShell.move(startingX, startingY);
   
    var xMove = -17;
    var yMove = -7;
   
    // Body fin
    rocket.rect(35, 14).attr({fill: '#fff', stroke: '#000'}).dmove(xMove, yMove);
    // Top fin
    rocket.polygon('0,0 -20,7 0,14').attr({fill: '#f00', stroke: '#000'}).dmove(xMove, yMove); 
    // Left fin
    rocket.polygon('35,14 20,14 45,29').attr({fill: '#f00', stroke: '#000'}).dmove(xMove, yMove);
    // Right fin
    rocket.polygon('35,0 20,0 45,-15').attr({fill: '#f00', stroke: '#000'}).dmove(xMove, yMove);
    
    rocket.rotate(-270);
}