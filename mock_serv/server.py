#!flask/bin/python

from flask import Flask, jsonify

import os

# Global definitions
app = Flask(__name__)

# -----------------------------
# Server code


# CardList mocking
@app.route('/cards', methods=['GET'])
def card_list_method():
    ownerId = 1
    name = 'test1'
    recipePhoto = 'https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png'
    isSaved = True
    commentCount = 5
    isLiked = False
    likeCount = 7

    return jsonify([
        {
            'id': 1,
            'ownerId': ownerId,
            'name': name,
            'recipePhoto': recipePhoto,
            'isSaved': isSaved,
            'commentCount': commentCount,
            'isLiked': isLiked,
            'likeCount': likeCount
        },
        {
            'id': 2,
            'ownerId': ownerId,
            'name': name,
            'recipePhoto': recipePhoto,
            'isSaved': isSaved,
            'commentCount': commentCount,
            'isLiked': isLiked,
            'likeCount': likeCount
        },
        {
            'id': 3,
            'ownerId': ownerId,
            'name': name,
            'recipePhoto': recipePhoto,
            'isSaved': isSaved,
            'commentCount': commentCount,
            'isLiked': isLiked,
            'likeCount': likeCount
        },
        {
            'id': 4,
            'ownerId': ownerId,
            'name': name,
            'recipePhoto': recipePhoto,
            'isSaved': isSaved,
            'commentCount': commentCount,
            'isLiked': isLiked,
            'likeCount': likeCount
        },
        {
            'id': 5,
            'ownerId': ownerId,
            'name': name,
            'recipePhoto': recipePhoto,
            'isSaved': isSaved,
            'commentCount': commentCount,
            'isLiked': isLiked,
            'likeCount': likeCount
        }
    ])


def main():
    # This launches server
    app.secret_key = os.urandom(32)
    app.run(debug=True)


if __name__ == '__main__':
    main()
