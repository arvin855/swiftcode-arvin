var app = angular.module('chatApp', ['ngMaterial']);
app.config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('purple')
        .accentPalette('blue');
});

app.controller('chatController', function ($scope) {
    $scope.messages = [
        {
            'sender': "USER",
            'text': 'Hello'
	},
        {
            'sender': "BOT",
            'text': 'Hello there '
	},
        {
            'sender': "USER",
            'text': 'whats the time'
	},
        {
            'sender': "BOT",
            'text': 'see your watch :P'
	}];
});