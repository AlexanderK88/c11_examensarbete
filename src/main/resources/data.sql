# -- USERS
# INSERT IGNORE INTO users (id, username, password, email, role)
# VALUES
#     (1, 'mangafan', 'password123', 'mangafan@example.com', 'USER'),
#     (2, 'otaku', 'securepass', 'otaku@example.com', 'USER'),
#     (3, 'admin123', 'adminpass', 'admin@example.com', 'ADMIN'),
#     (4, 'reader', 'readpass', 'reader@example.com', 'USER'),
#     (5, 'critic', 'criticpass', 'critic@example.com', 'USER');
#
# -- LISTS
# INSERT INTO lists (id, list_name, description, user_id)
# VALUES
#     (1, 'Top Shonen', 'Best shonen mangas ever', 1),
#     (2, 'Ongoing Favorites', 'Mangas that are still running', 2),
#     (3, 'Completed Classics', 'All-time classics that are finished', 3),
#     (4, 'Fantasy Picks', 'Fantasy-themed mangas', 4),
#     (5, 'Critics Choice', 'Highly rated by critics', 5);
#
#
# INSERT INTO saved_manga (user_id, manga_id)
# VALUES
#     (1, 101),
#     (1, 102),
#     (1, 103),
#     (2, 102),
#     (2, 202),
#     (2, 402),
#     (3, 101),
#     (3, 103),
#     (3, 301),
#     (4, 203),
#     (4, 302),
#     (4, 403),
#     (5, 301),
#     (5, 302),
#     (5, 201);
#
# -- REVIEWS
# INSERT INTO reviews (id, review_text, title, spoiler, rating, user_id, manga_id, timestamp)
# VALUES
#     (1, 'A masterpiece of storytelling!', 'Naruto Review', false, 9.0, 1, 101, NOW()),
#     (2, 'An epic adventure with great character arcs.', 'One Piece Review', false, 9.5, 2, 102, NOW()),
#     (3, 'Too many fillers but a solid story overall.', 'Bleach Review', true, 8.2, 1, 103, NOW()),
#     (4, 'A dark, thrilling masterpiece.', 'Death Note Review', false, 9.8, 5, 301, NOW()),
#     (5, 'Heartwarming yet action-packed.', 'Demon Slayer Review', false, 9.1, 4, 203, NOW());
#
# -- COMMENTS
# INSERT INTO comments (id, comment_text, user_id, review_id, timestamp)
# VALUES
#     (1, 'Totally agree with your review!', 2, 1, NOW()),
#     (2, 'This manga changed my life.', 4, 1, NOW()),
#     (3, 'I think it was overrated.', 3, 3, NOW()),
#     (4, 'Well-written review, I loved it!', 1, 4, NOW()),
#     (5, 'Thanks for the recommendation!', 2, 5, NOW());
#
# -- NOTIFICATIONS
# INSERT INTO notifications (id, message, read_status, user_id, timestamp)
# VALUES
#     (1, 'New manga added to your list: Naruto', false, 1, NOW()),
#     (2, 'One Piece has a new chapter!', false, 2, NOW()),
#     (3, 'Your review on Bleach got a comment.', true, 1, NOW()),
#     (4, 'Death Note has reached a new popularity ranking!', false, 5, NOW()),
#     (5, 'Dragon Ball is now trending!', true, 4, NOW());
