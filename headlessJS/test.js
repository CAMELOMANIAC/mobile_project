import notifee from '@notifee/react-native';

export default async taskData => {
  // 여기에 비동기 작업 로직 작성
  async function onDisplayNotification() {
    const channelId = await notifee.createChannel({
      id: 'default',
      name: 'Default Channel',
    });

    await notifee.displayNotification({
      title: '알림 제목',
      body: '알림 내용입니다.',
      android: {
        channelId,
        smallIcon: 'ic_launcher', // AndroidManifest에 등록된 아이콘 이름
      },
    });
  }
  onDisplayNotification();
};
