SELECT ip FROM parser.log_record
where (`date` between '2017-01-01.15:00:00' and '2017-01-01.15:59:59')
group by ip
having count(ip) >= 200;


SELECT request FROM parser.log_record
where ip = '192.168.11.231';