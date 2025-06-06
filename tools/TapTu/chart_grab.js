// run this in https://vibe.naver.com/chart
const top100Section = document.querySelector('.end_section');
const rows = top100Section.querySelectorAll('.list_track_row')
const songs = []
for (const row of rows) {
  const rankText = row.querySelector('.rank .num')?.textContent.trim();
  const rank = parseInt(rankText, 10);
  if (isNaN(rank) || rank > 20) continue;
  const thumbnail = row.querySelector('img')?.src || '';
  const title = row.querySelector('.song .title')?.textContent.trim() || '';
  const artist = row.querySelector('.artist .link_artist .text')?.textContent.trim() || '';
  const song = {
    rank, title, artist, thumbnail,
  }
  songs.push(song)
}

console.log(songs)
console.log(JSON.stringify(songs, null, 2))