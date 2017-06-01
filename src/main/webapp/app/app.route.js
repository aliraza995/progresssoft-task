angular.module('ps',['ui.router','ngResource','ps.controllers','ps.services','ngAnimate','ui.bootstrap','dialogs','base64']);

angular.module('ps').config(function($stateProvider,$httpProvider,$urlRouterProvider){
    $urlRouterProvider.otherwise('/upload');

    $stateProvider.state('upload', {
        url:'/index',
        templateUrl:'app/components/Upload/uploadView.html',
        controller:'UploadController'
    });
}).run(['$rootScope', '$state', '$stateParams','$location',
        function($rootScope, $state, $stateParams, $location) {
	$location.url('/index');
}]);

