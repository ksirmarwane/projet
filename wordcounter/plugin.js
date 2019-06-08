!function($) {
    
	tinymce.init({
		    plugins: "paste",
		    paste_as_text: true
	 });
	tinymce.PluginManager.add('wordcounter', function(editor, url) {
   
		 // Listen for paste event, add "Paste as plain text" callback
            editor.onPaste.add(function (editor, e) {
          	
         // Prevent default paste behavior
            e.preventDefault();
            // Check for clipboard data in various places for cross-browser compatibility.
            // Get that data as text.
            var  content = ((e.originalEvent || e).clipboardData || window.clipboardData).getData('Text');
            var  position = content.indexOf("http://devtools.spectrumgroupe.fr:10009/browse/");
            var jirabaseurl = "http://devtools.spectrumgroupe.fr:10009/browse/";
            var  issue =content.substring(jirabaseurl.length);
           if ( position >= 0) {
          	//<span> <img alt="Smiley face" height="20" width="20" src="/jcms/plugins/DemoWysiwygPlugin/images/jira.png"/> </span>
    tinymce.activeEditor.execCommand('mceInsertContent', false, '<a href="'+ content +'">'+issue+'</a>'); 
           }
           else{
          	 tinymce.activeEditor.execCommand('mceInsertContent', false, content);
          		
          	  }           
  	  });
    
     
    // Register the button so it can be declared in the toolbar configuration
    editor.addButton('countword', {
      tooltip : 'wordcounter.btn.tooltip', // I18N property name, declared in en.prop/fr.prop without the "wysiwyg.plugins." prefix     
      icon : 'icomoon icomoon-ruler', // Icon name from icon set available in JPlatform (Glyphicon, Icomoon, ...)
     
      // Action performed when button is clicked
      onclick : function() {

    	  // get selected text
    	  var selectedText = tinymce.activeEditor.selection.getContent({ format : 'text' });
    	  localStorage.setItem('selected', selectedText);
    	  
          tinymce.activeEditor.windowManager.open({
        	   url: 'http://localhost:8080/jcms/plugins/Jiraplugin/jsp/wysiwyg.jsp',
        	   title: 'Create Issue',
        	   width: 750,
        	   height: 600
              
        	});
        
       // document.getElementById("data").value = text_value;       
        
        },
      
      // Enable the button only when text is selected, disabled by default
      disabled: true,      
      onPostRender : function() {
        var btn = this;
        editor.on('NodeChange', function(e) {
          btn.disabled(tinymce.activeEditor.selection.isCollapsed());
        });
      }
    });
    
  });
	
}(window.jQuery);