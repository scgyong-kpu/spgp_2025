import os
import json
import base64
import urllib.request

def sanitize_filename(s):
    # 파일명에 쓸 수 없는 문자 제거
    return "".join(c for c in s if c.isalnum() or c in (' ', '_', '-')).rstrip()

def save_base64_image(data_url, filepath):
    header, base64_data = data_url.split(',', 1)
    image_data = base64.b64decode(base64_data)
    with open(filepath, 'wb') as f:
        f.write(image_data)

def download_image(url, filepath):
    urllib.request.urlretrieve(url, filepath)

def main():
    save_dir = "thumbnails"
    os.makedirs(save_dir, exist_ok=True)

    with open('songs.json', 'r', encoding='utf-8') as f:
        songs = json.load(f)

    for i, song in enumerate(songs, start=1):
        title = sanitize_filename(song.get('title', f'song_{i}'))
        artist = sanitize_filename(song.get('artist', 'unknown'))
        thumb = song.get('thumbnail')

        if not thumb:
            print(f"[!] {title} - {artist}: 썸네일 정보 없음")
            continue

        # 기본 확장자 png로 할당 (Base64 아니면 jpg로 바꿀 수도 있음)
        ext = "png" if thumb.startswith("data:image/png") else "jpg"
        # filename = f"{i:03d}_{title}_{artist}.{ext}"
        filename = f"cover_{i:03d}.{ext}"
        filepath = os.path.join(save_dir, filename)

        try:
            if thumb.startswith("data:image/"):
                # base64 이미지 저장
                save_base64_image(thumb, filepath)
                print(f"[✔] Base64 이미지 저장: {filename}")
            else:
                # 일반 URL 다운로드
                download_image(thumb, filepath)
                print(f"[✔] URL 이미지 다운로드: {filename}")

        except Exception as e:
            print(f"[✘] {title} - {artist}: 이미지 저장 실패")
            print("   오류:", e)

if __name__ == "__main__":
    main()