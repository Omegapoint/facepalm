/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var PostModal = function () {
    this.$elm = $($.parseHTML('' +
        '<div class="modal fade" tabindex="-1" role="dialog">' +
        '   <div class="modal-dialog">' +
        '       <div class="modal-content">' +
        '           <div class="modal-header">' +
        '               <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
        '               <h4 class="modal-title">Upload image</h4>' +
        '           </div>' +
        '           <div class="modal-body">' +
        '               <form method="POST" enctype="multipart/form-data" action="/postImage"> ' +
        '                   <div class="input-group full-width"> ' +
        '                       <label for="title">Title</label>' +
        '                       <input id="title" name="title" pattern=".{1,}" type="text" class="form-control" required placeholder="Title"> ' +
        '                   </div> ' +
        ' 		            <h4>File to upload:</h4> ' +
        '                   <input class="btn btn-default" pattern="*.jpg" type="file" name="file"><br /> ' +
        '                   <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>' +
        '                   <button type="submit" class="btn btn-primary">Post</button>' +
        '               </form>' +
        '           </div><!-- /.modal-body -->' +
        '       </div><!-- /.modal-content -->' +
        '   </div><!-- /.modal-dialog -->' +
        '</div><!-- /.modal -->'));
};

PostModal.prototype.show = function () {
    var self = this;
    $("body").append(self.$elm);

    self.$elm.modal('show');
    self.$elm.on('hidden.bs.modal', function () {
        self.$elm.remove();
        $('.modal-backdrop').remove();
    });
};