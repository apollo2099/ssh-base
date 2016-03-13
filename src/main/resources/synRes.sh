#!/bin/sh
/usr/bin/rsync -av /var/log/www.solive.kv/access_log testuser@10.0.1.219::store --password-file=/etc/client/rsync.pwd