var app = angular.module('myApp', []);

app.directive('file', function() {
    return {
        require:"ngModel",
        restrict: 'A',
        link: function($scope, el, attrs, ngModel){
            el.bind('change', function(event){
                var files = event.target.files;
                var file = files[0];

                ngModel.$setViewValue(file);
                $scope.$apply();
            });
        }
    };
});

app.controller('userCtrl', ['$scope', '$rootScope', '$window', 'UserFactory', function($scope, $rootScope, $window, UserFactory){
	$scope.user = {
		username: '',
		password: '',
		email:'',
		country:'',
		card_nr:''
	};
	$scope.logged = false;
	$scope.login = function(){
		let toSend = angular.copy($scope.user);
		UserFactory.login(toSend).then(function(response){
			if(response.data){
				UserFactory.getUserByName(toSend.username).then(function(response){
					if(response.data){
						sessionStorage.setItem('user', JSON.stringify(response.data));
						$window.location.href = "index.html";
					}else{
						alert('Error!');
					}
				});
			}else{
				alert('Check if you misspeled username or password!');
				$scope.logged = 'false';
			}
		});
	};

	$scope.register = function(){
		let toSend = angular.copy($scope.user);
		//if($scope.validate()){
			UserFactory.register(toSend).then(function(response){
				if(response.data){
					$window.location.href = "login.html";
				}else{
					alert('Username '+ $scope.user.username + ' is already taken');
					$scope.logged = 'false';
				}
			});
		//}
	};
	
//	$scope.validate = function(){
//		if($scope.user.username.trim()=='' || $scope.user.password.trim()=='' || $scope.user.email.trim()=='' ||
//				$scope.user.country.trim()=='' || $scope.user.card_nr.trim()==''){
//			alert('All fields are required!');
//			return false;
//		}
//		return true;
//	}
	$scope.getUserByName = function(str){
		UserFactory.getUserByName(str).then(function(response){
			if(response.data){
				sessionStorage.setItem('user', JSON.stringify(response.data));
			}else{
				alert('There is no user with name: '+toSend.username);
			}
		});
	};
}]);

app.factory('UserFactory', ['$http', function($http){
	let fact = {};
	
	fact.login = function(user){
		return $http.post("http://localhost:8080/Domaci7/rest/user/login", user);
	}

	fact.register = function(user){
		return $http.post("http://localhost:8080/Domaci7/rest/user/register", user);
	}
	
	fact.getUserByName = function(username){
		return $http.get("http://localhost:8080/Domaci7/rest/user/get/"+username);
	}
	return fact;

}]);

app.controller('photoCtrl', ['$scope', '$window', 'PhotoFactory', function($scope, $window, PhotoFactory){
	var vm = this;
	vm.photo = {
		photo_id: '',
		user_id: '',
		photo_name:'',
		price:''
	};
	vm.page_nr = 1;
	vm.addToCart = function(id){
		console.log('id: '+id);
		var pictures = angular.copy(JSON.parse(sessionStorage.getItem('cart')));
		sessionStorage.removeItem('cart');
		pictures.push(vm.photos[id]);
		console.log(vm.photos[id]);
		console.log(pictures);
		sessionStorage.setItem('cart', JSON.stringify(pictures));
	};
	
	vm.searchNextFive = function(){
		let toSend = angular.copy(vm.page_nr);
		PhotoFactory.searchNextFive(toSend).then(function(response){
			vm.photos = angular.copy(response.data);
			console.log(vm.photos);
		});
	};

	vm.getAll = function(){
		let toSend = angular.copy();
		PhotoFactory.getAll().then(function(response){
			vm.photos = angular.copy(response.data);
			console.log(vm.photos);
		});
	};
	vm.prev = function(){
		vm.page_nr--;
		vm.searchNextFive();
	};
	
	vm.next = function(){
		vm.page_nr++;
		vm.searchNextFive();
	};
	
	vm.photos = vm.searchNextFive();
}]);

app.factory('PhotoFactory', ['$http', function($http){
	let fact = {};
	
	fact.searchNextFive = function(page_nr){
		return $http.get("http://localhost:8080/Domaci7/rest/photo/next/"+page_nr);
	}

	fact.getAll = function(){
		return $http.get("http://localhost:8080/Domaci7/rest/photo");
	}
	
	fact.getFilteredPictures = function(filter){
		var config = { headers : {'Content-Type': 'application/json; charset=utf-8'}};
		var data = JSON.stringify(filter)
		return $http.post("http://localhost:8080/Domaci7/rest/photo/filter", data, config);
	}
	
	return fact;

}]);

app.controller('userLogCtrl', ['$scope', '$rootScope', '$window', function($scope, $rootScope, $window){
	
	$scope.userLoggedIn = function(user){
		if(user===null){
			return false;
		}
		console.log(user);
		return true;
	};
	
	$scope.logout = function(){
		sessionStorage.removeItem('user');
		$scope.logged = false;
		console.log('zanklod');
		$window.location.href = "login.html";
	}
	
	$scope.uploadTen = function(){
		$window.location.href = "upload10.html";
	}
	
	$scope.uploadPicture = function(){
		$window.location.href = "upload.html";
	}
	
	$scope.user = JSON.parse(sessionStorage.getItem('user'));
	$scope.logged = $scope.userLoggedIn($scope.user);
}]);

app.controller('uploadTenCtrl', ['$scope', '$rootScope', '$window', function($scope, $rootScope, $window){
	$scope.numbers = [0, 1, 2, 3, 4, 5, 6 ,7 ,8 ,9];
    $scope.files = [null, null, null, null, null, null, null, null, null, null];
    
    $scope.send = function(){
    	for(var f in $scope.files){
      	if($scope.files[f]===null){
        	console.log(f+': null');
        }else{
        	console.log($scope.files[f].name+' '+f);
        }
      	
      }
    }
}]);

app.controller('uploadPictureCtrl', ['$scope', '$rootScope', '$window', 'UpdatePictureFactory', function($scope, $rootScope, $window, UpdatePictureFactory){
	$scope.getCategories = function(){
		UpdatePictureFactory.getCategories().then(function(response){
			if(response.data){
				$scope.categories = angular.copy(response.data);
				console.log($scope.categories);
			} else {
				$scope.categories = [];
			}
		});
	}

	$scope.categories = $scope.getCategories();
	$scope.file = '';
	$scope.picture = {
		user_id: -1,
		name: '',
		path: '',
		extension: '',
		date: 0,
		resolution_id: 0,
		priceHD: 0,
		priceFHD: 0,
		price4K: 0,
		sold_nr: 0,
		rates_nr: 0,
		rates_sum: 0,
		place: '',
		description: '',
		is_verified: 0,
		category_id: 0,
		test: 0
	};

	$scope.checked_hd = true;
	$scope.checked_fhd = false;
	$scope.checked_4k = false;
	
	$scope.data = {}; //init variable
    $scope.click = function() { //default function, to be override if browser supports input type='file'
      $scope.data.alert = "Your browser doesn't support HTML5 input type='File'"
    }

    var fileSelect = document.createElement('input'); //input it's not displayed in html, I want to trigger it form other elements
    fileSelect.type = 'file';

    if (fileSelect.disabled) { //check if browser support input type='file' and stop execution of controller
      return;
    }

    $scope.click = function() { //activate function to begin input file on click
      fileSelect.click();
    }

    fileSelect.onchange = function() { //set callback to action after choosing file
      var f = fileSelect.files[0],
        r = new FileReader();

      	r.onloadend = function(e) { //callback after files finish loading
        $scope.data.b64 = e.target.result;
        $scope.$apply();
        var ret = $scope.data.b64.split(',')[1];
        var ret2= $scope.data.b64.split(',')[0];
        console.log("RET " + ret2);
        if (ret2.includes("png")) {
        	$scope.picture.extension = "png";
        }
        else if(ret2.includes("jpg") || ret2.includes("jpeg")) {
        	$scope.picture.extension = "jpg";
        }
        
        console.log("$scope extension " + $scope.picture.extension);
//        var strImage = $scope.data.b64.replace(/^data:image\/[a-z]+;base64,/, "");
        //console.log("ret " + ret);
//        console.log("strImage " + strImage);
        $scope.data.b64 = angular.copy(ret);
        console.log("$SCOPE DATA B64 " + $scope.data.b64); //replace regex if you want to rip off the base 64 "header"
        
        $scope.picture.path = angular.copy($scope.data.b64);
        //here you can send data over your server as desired
      }

      r.readAsDataURL(f); //once defined all callbacks, begin reading the file

    };

	$scope.upload = function() {
		
		console.log("before $scope.picture.path: " + $scope.picture.path);
		
//		console.log("$scope.picture.path: " + $scope.picture.path);
		
		if (sessionStorage.getItem('user') == null) {
			alert("Login first!");
			$window.location.href = "login.html";
		}

// TODO: If any field is empty then don't upload.
 		
		console.log("Uploading photo...  " + $scope.picture.path + " ASD.");
		
//		var src_str = $scope.picture.path.split(".",2);
//		$scope.picture.path = src_str[0];
//		$scope.picture.extension = src_str[1];
		
		$scope.picture.category_id = $scope.picture.category_id;
		console.log('category is ' + $scope.picture.category_id);
		$scope.picture.user_id = (JSON.parse(sessionStorage.getItem('user'))).id
		console.log('logged user is ' + $scope.picture.user_id);
		console.log('-');
		console.log($scope.picture);
		
		if($scope.checked_hd) {
			console.log("Picture price HD: " + $scope.picture.priceHD);
			$scope.picture.resolution_id = 0;
		}
		if($scope.checked_fhd) {
			console.log("Picture price FHD: " + $scope.picture.priceFHD);
			$scope.picture.resolution_id = 1;
		}
		if($scope.checked_4k){
			console.log("Picture price 4K: " + $scope.picture.price4K);
			$scope.picture.resolution_id = 2;
		}
		
		let toSend = angular.copy($scope.picture);
		UpdatePictureFactory.addPicture(toSend).then(function(response){
			console.log('add picture Upload');
			if(response.data){
				alert("Succesfully uploaded photo!");
				//$window.location.href = "index.html";
			} else {
				alert('Error!');
			}
		});
	}

}]);


app.factory('UpdatePictureFactory', ['$http', function($http){
	let fact = {};
	
	fact.getCategories = function(){
		return $http.get("http://localhost:8080/Domaci7/rest/photo/categories");
	}
	
	fact.addPicture = function(photo){
		return $http.post("http://localhost:8080/Domaci7/rest/photo/add", photo);
	}
	
	fact.getPictures = function(photo){
		return $http.post("http://localhost:8080/Domaci7/rest/photo", photo);
	}
	
	
	return fact;

}]);

app.controller('authorsCtrl', ['$scope', '$window', 'AuthorsFactory', 'UserFactory', function($scope, $window, AuthorsFactory, UserFactory){
	$scope.addToCart = function(id){
		console.log('id: '+id);
		var pics = angular.copy(JSON.parse(sessionStorage.getItem('cart')));
		sessionStorage.removeItem('cart');
		pics.push($scope.pictures[id]);
		console.log($scope.pictures[id]);
		console.log(pics);
		sessionStorage.setItem('cart', JSON.stringify(pics));
	};
	
	$scope.getUsers = function(){
		AuthorsFactory.getUsers().then(function(response){
			if(response.data){
				$scope.authors = angular.copy(response.data);
				console.log($scope.authors);
			}else{
				console.log('Error');
			}
		});
	}
	
	$scope.getUsersByName = function(){
		let username = angular.copy($scope.name);
		if(username===null || username.length===0){
			AuthorsFactory.getUsers().then(function(response){
				if(response.data){
					$scope.authors = angular.copy(response.data);
					console.log($scope.authors);
				}else{
					console.log('Error');
				}
			});
		}else{
			AuthorsFactory.getUsersByName(username).then(function(response){
				if(response.data){
					$scope.authors = angular.copy(response.data);
					console.log($scope.authors);
				}else{
					console.log('Error');
				}
			});
		}
	}
	$scope.viewAuthor = function(name){
		UserFactory.getUserByName(name).then(function(response){
			if(response.data){
				sessionStorage.setItem('author', JSON.stringify(response.data));
				console.log(name);
				$window.location.href = "author.html";
			}else{
				alert('There is no user with name: '+toSend.username);
			}
		});
	}
	
	$scope.getNextFive = function(){
		if($scope.author===null){
			console.log('Author is null');
		}else{
			let user = {username:'', n:'', id:-1};
			user.username = angular.copy($scope.author.username);
			user.n = angular.copy($scope.page_nr);
			user.id = angular.copy($scope.author.user_id);
			console.log(user.username+' '+user.n+' '+user.id);
			AuthorsFactory.getNextFive(user).then(function(response){
				if(response.data){
					$scope.pictures = angular.copy(response.data);
				}else{
					console.log('error');
				}
			});
		}
	}
	
	$scope.prev = function(){
		$scope.page_nr--;
		$scope.getNextFive();
	};
	
	$scope.next = function(){
		$scope.page_nr++;
		$scope.getNextFive();
	};
	
	$scope.ban = function(){
		let username = angular.copy($scope.author.username);
		console.log(username);
		AuthorsFactory.ban(username).then(function(response){
			if(response.data){
				console.log('banned');
			}else{
				console.log('error');
			}
		});
	};
	
	$scope.getCommentsForAuthor = function(){
		if($scope.author!=null){
			AuthorsFactory.getCommentsForAuthor($scope.author.username).then(function(response){
				if(response.data){
					$scope.comments = response.data;
					console.log($scope.comments);
				}else{
					console.log('Error finding comments!');
				}
			});
		}else{
			console.log('Author is null!');
		}
	};
	
	$scope.name = '';
	$scope.authors = $scope.getUsers();
	$scope.author = sessionStorage.getItem("author") === null?null:JSON.parse(sessionStorage.getItem('author'));
	$scope.user = sessionStorage.getItem('user') === null?null:JSON.parse(sessionStorage.getItem('user'));
	$scope.page_nr = 1;
	$scope.pictures = $scope.getNextFive();
	$scope.comments = $scope.getCommentsForAuthor();
}]);

app.factory('AuthorsFactory', ['$http', function($http){
	let fact = {};
	
	fact.getUsers = function(){
		return $http.get("http://localhost:8080/Domaci7/rest/user");
	}
	
	fact.getUsersByName = function(username){
		return $http.get("http://localhost:8080/Domaci7/rest/user/getu/"+username);
	}
	
	fact.getNextFive = function(user){
		return $http.post("http://localhost:8080/Domaci7/rest/photo/nextu", user);
	}
	
	fact.ban = function(username){
		return $http.get("http://localhost:8080/Domaci7/rest/user/ban/"+username);
	}
	
	fact.getCommentsForAuthor = function(username){
		return $http.get("http://localhost:8080/Domaci7/rest/user/comments2/"+username);
	}
	return fact;

}]);

app.controller('filtersCtrl', ['$scope', '$window', 'UpdatePictureFactory', 'PhotoFactory', function($scope, $window, UpdatePictureFactory, PhotoFactory){
	$scope.addToCart = function(id){
		console.log('id: '+id);
		var pics = angular.copy(JSON.parse(sessionStorage.getItem('cart')));
		sessionStorage.removeItem('cart');
		pics.push($scope.photos[id]);
		console.log($scope.photos[id]);
		console.log(pics);
		sessionStorage.setItem('cart', JSON.stringify(pics));
	};
	
	$scope.getCategories = function(){
		console.log('ulazak.');
		UpdatePictureFactory.getCategories().then(function(response){
			if(response.data){
				$scope.categories = angular.copy(response.data);
				console.log($scope.categories);
			}else{
				$scope.categories = [];
			}
		});
	}
	
	$scope.handleRadioClick = function(click){
		
		$scope.param_selected = click+'';
	};
	$scope.handleSortRadioClick = function(click){
		
		if(click==='Asc'){
			$scope.sort_type='Asc';
		}else{
			$scope.sort_type='Desc';
		}
	};
	
	$scope.returnCategory = function(){
		for(cat in $scope.categories){
			if($scope.categories[cat].category_name==$scope.selected){
				return angular.copy($scope.categories[cat]);
			}
		}
		return null;
	}
	
	$scope.buy = function(index){
		console.log('In buy function!');
		console.log($scope.photos[index]);
	}
	
	$scope.getFilteredPictures = function(){
		$scope.filter.photo_name = angular.copy($scope.photo_name);
		console.log(typeof($scope.filter.photo_name));
		$scope.filter.author_name = angular.copy($scope.author_name);
		console.log(typeof($scope.filter.author_name));
		$scope.filter.keyword = angular.copy($scope.keyword);
		console.log(typeof($scope.filter.keyword));
		if($scope.selected.category_id!=null)
			$scope.filter.category_id = $scope.selected.category_id;
		console.log(typeof($scope.filter.category_id));
		let param = angular.copy($scope.param_selected);
		$scope.filter.sort_param = angular.copy($scope.sort_params[param]);
		console.log(typeof($scope.filter.sort_param));
		$scope.filter.sort_type = angular.copy($scope.sort_type);
		console.log(typeof($scope.filter.sort_type));
		let f = angular.copy($scope.filter);
		console.log(f);
		PhotoFactory.getFilteredPictures(f).then(function(response){
			if(response.data){	
				$scope.photos = angular.copy(response.data);
			}else{
				console.log('Error!');
			}
		});
	};
	
	$scope.categories = $scope.getCategories();
	$scope.selected = '';
	$scope.photo_name = '';
	$scope.author_name = '';
	$scope.keyword = '';
	$scope.param_selected=0;
	$scope.sort_type='Asc';
	$scope.sort_params = ['Date', 'Numbers sold', 'Price', 'Photo name', 'Rate'];
	$scope.type = ['Asc', 'Desc'];
	$scope.filter = {
		photo_name:'',
		author_name:'',
		keyword:'',
		category_id:-1,
		sort_param:'',
		sort_type:''
	};
	$scope.photos = [];
}]);

app.factory('FilterFactory', ['$http', function($http){
	let fact = {};
	
	return fact;

}]);

app.controller('cartCtrl', ['$scope', '$window', 'CartFactory', function($scope, $window, CartFactory){
	$scope.pictures = JSON.parse(sessionStorage.getItem('cart'));
	$scope.user = JSON.parse(sessionStorage.getItem('user'));
	$scope.buy = function(id){
		console.log($scope.pictures[id]);
		let b = {seller_id:'', buyer_id:'', picture_id:'', price:0};
		b.buyer_id = $scope.user.user_id;
		b.seller_id = $scope.pictures[id].user_id;
		b.picture_id = $scope.pictures[id].photo_id;
		b.price = $scope.pictures[id].price;
		CartFactory.buy(b).then(function(response){
			if(response.data){
				console.log('Buying finished successfully');
			}else{
				console.log('Buying failed');
			}
		});
		$scope.remove(id);
	}
	
	$scope.remove = function(id){
		$scope.pictures.splice(id, 1);
		sessionStorage.removeItem('cart');
		console.log($scope.pictures);
		sessionStorage.setItem('cart', JSON.stringify($scope.pictures));
	}
}]);

app.factory('CartFactory', ['$http', function($http){
	let fact = {};
	
	fact.buy = function(buying){
		return $http.post("http://localhost:8080/Domaci7/rest/photo/buy", buying);
	}
	
	return fact;

}]);