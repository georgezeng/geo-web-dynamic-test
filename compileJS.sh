browserify -t [ babelify --presets [ react ] ] src/main/js/$1/main.js -o src/main/webapp/js/$2-react.js
browserify -g uglifyify src/main/webapp/js/$2-react.js > src/main/webapp/js/$2.min.js
rm src/main/webapp/js/$2-react.js